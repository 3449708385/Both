package com.yishenxiao.commons.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.EasemobGroupBean;
import com.yishenxiao.commons.beans.QQGroup;
import com.yishenxiao.commons.beans.QQMember;
import com.yishenxiao.commons.beans.RegisterBean;
import com.yishenxiao.commons.service.QQGroupService;
import com.yishenxiao.commons.service.QQMemberService;
import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.MD5Utils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("qqdatapreproce")
public class QQDataPreproceController {
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("qQGroupService")
	private QQGroupService qQGroupService;
	
	@Autowired(required=false)@Qualifier("qQMemberService")
	private QQMemberService qQMemberService;
	
	@Autowired(required=false)@Qualifier("userRelationGroupService")
	private UserRelationGroupService userRelationGroupService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisService;
	
	/**
	 * @info 预创建用户
	 * @return
	 */
	@RequestMapping("/signupUser")
	public @ResponseBody ReturnInfo signupUser(){			
		int eventCounts=5000;
		//群成员
		int memberCounts=qQMemberService.queryByCounts();
		int bCount= memberCounts/eventCounts+1;
        for(int m=0;m<bCount;m++){
        	List<QQMember> qQMemberList = qQMemberService.queryQQMemberByCounts(m*eventCounts,eventCounts);
        	System.out.println(new Date()+" qqmember:"+m*eventCounts);
			if(qQMemberList.size()!=0){
				List<RegisterBean> userNameList = new ArrayList<RegisterBean>();
	        	List<String> heavySentence = new ArrayList<String>();
				for(QQMember qQMember:qQMemberList){
					if(heavySentence.contains(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase())){
						continue;
					}else{
						//判断redis是否存在这个数据库
						if(redisService.opsForValue().get(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase())!=null){
							continue;
						}
						//判断数据库是否存在这个用户	
						List<User> userTemp=userService.queryByUserNameList(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase());
						if(userTemp.size()>0){
							continue;
						}
						heavySentence.add(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase());
						redisService.opsForValue().set(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase(), qQMember.getNickname().toString());
					}
					RegisterBean registerBean = new RegisterBean();
					registerBean.setUsername(MD5Utils.getMd5(qQMember.getUserId().toString()).toLowerCase());
					registerBean.setNickname(qQMember.getNickname());
					registerBean.setPassword(new Sha256Hash(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.password")).toHex());
					userNameList.add(registerBean);
				}
				//注册用户
				EasemobUtils.EasemobBatchRegisteredUsersNickList(userNameList);
				String extensionOne = PropertiesUtils.getInfoConfigProperties().getProperty("user.extension.one");
				//环信数据入库
				for(String easemobUser:heavySentence){
					User user = new User();
					user.setUsername(easemobUser);
					user.setCreatetime(new Date());
					user.setIslogin(0);
					user.setPasswd(new Sha256Hash(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.password")).toHex());
					user.setState(0);
					user.setType(1);
					user.setUpdatetime(new Date());
					int i = userService.insertUser(user);
					if(i==1){
						String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.path")+easemobUser+".jpg";
						if(!new File(headicon).exists()){
							headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+"user.png";	
						}else{
							headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+easemobUser+".jpg";
						}
						List<User> tempUser = userService.queryByUserNameList(user.getUsername());
						UserSchedule userSchedule = new UserSchedule();
						userSchedule.setUserid(tempUser.get(0).getId());
						userSchedule.setCreatetime(new Date());
						userSchedule.setNickname(redisService.opsForValue().get(easemobUser).toString());
						userSchedule.setPurseaddress("yishengxiao");
						userSchedule.setPaypwone(0);
						userSchedule.setHeadicon(headicon);
						userSchedule.setMonetary(new BigDecimal(extensionOne));
						userSchedule.setPaymentpw("");
						userSchedule.setUpdatetime(new Date());
						userScheduleService.insertUserSchedule(userSchedule);
					}
				}
			}		
		}
        System.out.println("群成员数据处理完成！");
		System.out.println("用户数据入库完成!");
		createGroup();
		batchGroupsFromPushUser();
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 预创建群
	 * @return
	 */
	public void createGroup(){
		List<User> userTemp=userService.queryByUserNameList(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
		if(userTemp.size()==0){
			//创建默认的群管理员
			List<RegisterBean> userNameList = new ArrayList<RegisterBean>();
			RegisterBean registerBean = new RegisterBean();
			registerBean.setUsername(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
			registerBean.setNickname(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
			registerBean.setPassword(new Sha256Hash(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.password")).toHex());
			userNameList.add(registerBean);
			EasemobUtils.EasemobBatchRegisteredUsersNick(userNameList);
			User user = new User();
			user.setAccount("");
			user.setCreatetime(new Date());
			user.setEmail("");
			user.setIslogin(0);
			user.setPasswd("yishengxiao");
			user.setPhone("");
			user.setState(0);
			user.setType(0);
			user.setUpdatetime(new Date());
			user.setUsername(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
			userService.insertUser(user);
			UserSchedule userSchedule = new UserSchedule();
			userSchedule.setCreatetime(new Date());
			String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+"user.png";
			userSchedule.setHeadicon(headicon);
			userSchedule.setMonetary(new BigDecimal(1));
			userSchedule.setNickname("");
			userSchedule.setPaymentpw("");
			userSchedule.setPaypwone(0);
			userSchedule.setPurseaddress("");
			userSchedule.setUpdatetime(new Date());
			List<User> usertemp = userService.queryByUserNameList(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
			userSchedule.setUserid(usertemp.get(0).getId());
			userScheduleService.insertUserSchedule(userSchedule);
		}
		//建群
		List<QQGroup> qQGroupList = qQGroupService.queryQQGroupData();
		List<EasemobGroupBean> easemobGroupBeanList = new ArrayList<EasemobGroupBean>();
		for(int i=0;i<qQGroupList.size();i++){
		  //查询群成员个数
		  int cou = qQMemberService.queryByCountsGroupId(qQGroupList.get(i).getGroupId());
		  if(cou<20){
			continue;
		  }
		  List<Group> grouptempList = groupService.queryByGroupData(qQGroupList.get(i).getGroupId().toString());
		  if(grouptempList.size()>0){
			  continue;
		  }
		  QQGroup qQGroup = qQGroupList.get(i);
		  EasemobGroupBean easemobGroupBean = new EasemobGroupBean();
		  easemobGroupBean.setGroupName(qQGroup.getGroupId().toString());
		  easemobGroupBean.setGroupIcon("http://p.qlogo.cn/gh/"+qQGroupList.get(i).getGroupId()+"/"+qQGroupList.get(i).getGroupId()+"/100");
		  easemobGroupBean.setUsername(qQGroup.getGroupId().toString());
		  easemobGroupBean.setGroupDesc("");
		  easemobGroupBean.setOwner(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
		  easemobGroupBean.setMaxusers(2000);
		  easemobGroupBean.setApproval(false);
		  easemobGroupBean.setGroupPublic(true);
		  easemobGroupBean.setGroupType(0L);	 
		  easemobGroupBean.setGroupCount(cou);
		  easemobGroupBeanList.add(easemobGroupBean);
		}
		List<Group> groupList =  EasemobUtils.EasemobBatchRegisteredGroups(easemobGroupBeanList);
		System.out.println("群用户数据开始入库！");
		if(groupList.size()!=0){
		  groupService.insertGroupList(groupList);
		}
	    System.out.println("用户数据入库完成！");
	}
	
	/**
	 * @info 预拉人入群
	 * @return
	 */
	public void batchGroupsFromPushUser(){
		List<QQGroup> qQGroupList = qQGroupService.queryQQGroupData();
		for(int i=0;i<qQGroupList.size();i++){
			try{
				int cou = qQMemberService.queryByCountsGroupId(qQGroupList.get(i).getGroupId());
				if(cou<20){
					 continue;
				}
				//根据ParentUserName判断群
			   List<QQMember> qQMemberList = qQMemberService.queryByGroupId(qQGroupList.get(i).getGroupId());
			   List<Group> groupList = groupService.queryByUserName(qQGroupList.get(i).getGroupId().toString());
			   if(groupList.size()!=1){
				   System.out.println(new Date()+" group_err:"+groupList);
				   continue;
			   }
			   List<String> memberList = new ArrayList<String>();
			   for(int z=0;z<qQMemberList.size();z++){
				   System.out.println("群成员： "+qQMemberList.get(z).getNickname());
				   //redis去重
				   if(redisService.opsForValue().get(groupList.get(0).getGroupname()+groupList.get(0).getId()+MD5Utils.getMd5(qQMemberList.get(z).getNickname()).toLowerCase())!=null){
					   continue;
				   }
				   //如果群id和用户id是已经存在的
				   List<User> userss = userService.queryByUserNameList(MD5Utils.getMd5(qQMemberList.get(z).getUserId().toString()).toLowerCase());
				   List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByDataByList(groupList.get(0).getId(),userss.get(0).getId());
				   if(userRelationGroupList.size()>0){
					  continue;
				   }
				   //redis加人				   
				   redisService.opsForValue().set(groupList.get(0).getGroupname()+groupList.get(0).getId()+MD5Utils.getMd5(qQMemberList.get(z).getNickname()).toLowerCase(), true);
				   memberList.add(MD5Utils.getMd5(qQMemberList.get(z).getUserId().toString()).toLowerCase());
			   }
			   if(memberList.size()!=0){
				   EasemobUtils.EasemobBatchGroupsFromPushUser(groupList.get(0).getEasemobgroupid(), memberList);
				   System.out.println("环信群id:"+groupList.get(0).getEasemobgroupid()+", 加人完成！数据库入库开始:");
				   //加入数据库
				   List<UserRelationGroup> userRelationGroupList = new ArrayList<UserRelationGroup>();
				   for(int c=0;c<memberList.size();c++){
				      UserRelationGroup userRelationGroup = new UserRelationGroup();
				      userRelationGroup.setCreatetime(new Date());
				      userRelationGroup.setDisturbstate(0);
				      userRelationGroup.setGagstate(0);
				      userRelationGroup.setUpdatetime(new Date());
				      //根据名字查询id
				      Long uid = userService.queryByUserNameList(memberList.get(c)).get(0).getId();
				      userRelationGroup.setUserid(uid);
				      userRelationGroup.setGroupid(groupList.get(0).getId());
				      userRelationGroupList.add(userRelationGroup);
				   }
				   userRelationGroupService.insertList(userRelationGroupList);
				   System.out.println("环信群id:"+groupList.get(0).getEasemobgroupid()+", 数据库关联完成!");
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		}
	}
}
