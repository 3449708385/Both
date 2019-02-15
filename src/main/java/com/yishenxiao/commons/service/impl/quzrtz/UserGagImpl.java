package com.yishenxiao.commons.service.impl.quzrtz;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yishenxiao.commons.beans.IpAddr;
import com.yishenxiao.commons.controller.DataController;
import com.yishenxiao.commons.service.IpAddrService;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserGagEasemobBean;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserService;

@DisallowConcurrentExecution
public class UserGagImpl  extends QuartzJobBean {
	
	private static Logger logger = LoggerFactory.getLogger(UserGagImpl.class);
	private GroupService groupService;
	private UserService userService;
	private UserRelationGroupService userRelationGroupService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		userGagBean();
		userGag();
	}

	public void userGagBean(){
		if(groupService==null){
			groupService = (GroupService)SpringContextUtils.getBean("groupService");
		}
		if(userService==null){
			userService = (UserService)SpringContextUtils.getBean("userService");
		}
		if(userRelationGroupService==null){
			userRelationGroupService = (UserRelationGroupService)SpringContextUtils.getBean("userRelationGroupService");
		}
	}
	
	private void userGag() {
		logger.info("每天开始用户禁言！");
		List<Group> groupList= groupService.queryByGroupCodeNotNull();
		for(int i=0;i<groupList.size();i++){
			Group group=groupList.get(i);
			List<UserRelationGroup> userRelationGroupList=userRelationGroupService.queryByGroupId(group.getId());
			List<Long> userIdList = new ArrayList<Long>();
			for(int z=0;z<userRelationGroupList.size();z++){
				userIdList.add(userRelationGroupList.get(z).getUserid());
			}
			List<User> userList = userService.queryByUserIdListAndPhoneNotNULL(userIdList);
			UserGagEasemobBean userGagEasemobBean = new UserGagEasemobBean();
			List<String> userNameList = new ArrayList<String>();
			for(int z=0;z<userList.size();z++){
				userNameList.add(userList.get(z).getUsername());				
			}
			userGagEasemobBean.setUsernames(userNameList);
			userGagEasemobBean.setMute_duration(-1);
			Properties pro = PropertiesUtils.getInfoConfigProperties();
			String str="http://a1.easemob.com/"+pro.getProperty("easemob.appkeymodel")+"/chatgroups/"+group.getEasemobgroupid()+"/mute";
	    	String params=JsonUtils.toJson(userGagEasemobBean);
	    	try {
				HttpClientUtils.httpPostWithJSONEasemob(str, params);
			} catch (Exception e) {
			}
		}
		logger.info("用户禁言结束！");
	}

}
