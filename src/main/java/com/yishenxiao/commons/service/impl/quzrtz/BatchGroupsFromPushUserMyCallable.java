package com.yishenxiao.commons.service.impl.quzrtz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.yishenxiao.commons.beans.SignupUser;
import com.yishenxiao.commons.beans.WechatMember;
import com.yishenxiao.commons.service.impl.easemob.EasemobChatGroup;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

public class BatchGroupsFromPushUserMyCallable implements Callable<SignupUser> {

	private Map<String,Object> map;
	private UserService userService;
	private UserScheduleService userScheduleService;
	private RedisTemplate<String,Object> redisService;
	private UserRelationGroupService userRelationGroupService;
	private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	private static Logger logger = LoggerFactory.getLogger(BatchGroupsFromPushUserMyCallable.class);
	
	private void getBean(){
		userService=(UserService) SpringContextUtils.getBean("userService");
		userRelationGroupService=(UserRelationGroupService) SpringContextUtils.getBean("userRelationGroupService");
		userScheduleService=(UserScheduleService) SpringContextUtils.getBean("userScheduleService");
		redisService=(RedisTemplate<String,Object>) SpringContextUtils.getBean("redisTemplate");
	}
	

	public BatchGroupsFromPushUserMyCallable(Map<String,Object> map){
		this.map = map;
	}
	
	@Override
	public SignupUser call() throws Exception {
      getBean();
      List<WechatMember> wechatMemberList = (List<WechatMember>) map.get("wechatMemberList");
      Group group = (Group) map.get("group");
	  for(int z=0;z<wechatMemberList.size();z++){
		   //判断用户是否存在
		   List<User> userss = userService.queryByUserNameList(wechatMemberList.get(z).getNicknamemd5());
		   if(userss.size()==0){
			  logger.error("拉人进群，用户不存在(本地)："+wechatMemberList.get(z).getNicknamemd5());
			  continue;
		   }
		   //判断用户是否存在
		   List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByDataByList(group.getId(),userss.get(0).getId());
		   if(userRelationGroupList.size()>0){
			  continue;
		   }
		   //环信处理
	       Object result = easemobChatGroup.addSingleUserToChatGroup(group.getEasemobgroupid(), userss.get(0).getUsername());
	       if(result.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	    	   if(easemobErrorInfo.getError_code()==400){
	    		   logger.error("预处理拉人入群，"+userss.get(0).getUsername()+" 环信用户不存在。");
	    	   }
	    	   if(easemobErrorInfo.getError_code()==403){
	    		   logger.error("预处理拉人入群，"+group.getEasemobgroupid()+" 环信群已满！");
	    		   break;
	    	   }
	    	   if(easemobErrorInfo.getError_code()==404){
	    		   logger.error("预处理拉人入群，"+group.getEasemobgroupid()+" 环信群不存在。");
	    		   break;
	    	   }
	    	   logger.error("预处理拉人入群，"+group.getEasemobgroupid()+"  "+userss.get(0).getUsername()+" 环信异常请处理！");
	       }
	       //本地数据处理
	       UserRelationGroup userRelationGroup = new UserRelationGroup();
	       userRelationGroup.setCreatetime(new Date());
	       userRelationGroup.setDisturbstate(0);
	       userRelationGroup.setGagstate(0);
	       userRelationGroup.setUpdatetime(new Date());
	       //根据名字查询id
	       userRelationGroup.setUserid(userss.get(0).getId());
	       userRelationGroup.setGroupid(group.getId());
	       userRelationGroupList.add(userRelationGroup);
		   int c=userRelationGroupService.insertList(userRelationGroupList);
		   if(c!=1){
			   Object obj=easemobChatGroup.removeSingleUserFromChatGroup(group.getEasemobgroupid(), userss.get(0).getUsername());
			   if(obj.toString().contains("error_code")){
				   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
				   if(easemobErrorInfo.getError_code()==403){
						  logger.error("环信移除多余数据报错(预加人)：被移除用户不在群组里等");
				   }
				   if(easemobErrorInfo.getError_code()==404){
						  logger.error("环信移除多余数据报错(预加人)：被移除的IM用户不存在，此群组id不存在");
				   }
				   logger.error(easemobErrorInfo.getError_code()+"  环信移除多余数据报错(预加人)！");
				}
		   }else{
			   //redis 加入用户与群关系
			   Object obj = redisService.opsForValue().get("newBatchGroupsFromPushUser");
			   if(obj!=null){
					List<String> newBatchGroupsFromPushUser = (List<String>) obj;
					newBatchGroupsFromPushUser.add("user:"+userss.get(0).getUsername()+" ,group:"+group.getEasemobgroupid());
					redisService.opsForValue().set("newSignupUser", newBatchGroupsFromPushUser);
			   }else{
					List<String> newBatchGroupsFromPushUser = new ArrayList<String>();
					newBatchGroupsFromPushUser.add("user:"+userss.get(0).getUsername()+" ,group:"+group.getEasemobgroupid());
					redisService.opsForValue().set("newSignupUser", newBatchGroupsFromPushUser);
			   }
		   }
	   }
	   SignupUser signupUser = new SignupUser();
	   Map<String,Object> map = new HashMap<String,Object>();
	   map.put("code", 200);
	   map.put("msg", "线程用户与群:"+group.getEasemobgroupid()+"关联数据   入库完成!");
	   signupUser.setMap(map);
	   return signupUser;
	}

}
