package com.yishenxiao.commons.service.impl.quzrtz;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.EasemobGroupBean;
import com.yishenxiao.commons.beans.RegisterBean;
import com.yishenxiao.commons.beans.SignupUser;
import com.yishenxiao.commons.beans.WechatGroup;
import com.yishenxiao.commons.beans.WechatMember;
import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.controller.PreprocessingController;
import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatGroupService;
import com.yishenxiao.commons.service.WechatMemberService;
import com.yishenxiao.commons.service.impl.easemob.EasemobChatGroup;
import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.MD5Utils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.easemob.RegisterUsers;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@DisallowConcurrentExecution
public class SinupEasemobUserServiceImpl extends QuartzJobBean{

	private static Logger logger = LoggerFactory.getLogger(SinupEasemobUserServiceImpl.class);
	
	private UserScheduleService userScheduleService;
	
	private UserService userService;
	
	private GroupService groupService;
	
	private WechatGroupService wechatGroupService;
	
	private WechatFriendService wechatFriendService;
	
	private WechatMemberService wechatMemberService;
	
	private UserRelationGroupService userRelationGroupService;
	
    private RedisTemplate<String,Object> redisService;
    
	
	private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	
	private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SinupSpringBean();
		Object obj = redisService.opsForValue().get("setOverlayOperation");
		if(obj==null){
			redisService.opsForValue().set("setOverlayOperation", "false");
		}
		if(redisService.opsForValue().get("setOverlayOperation")!=null && redisService.opsForValue().get("setOverlayOperation").toString().equals("true")){
			return;
		}else{
		  signupUser();
		}
	}
	
	public void SinupSpringBean(){
		if(userScheduleService==null){
		    userScheduleService = (UserScheduleService)SpringContextUtils.getBean("userScheduleService");
		}
		if(userService==null){
			userService = (UserService)SpringContextUtils.getBean("userService");
		}
		if(groupService==null){
			groupService = (GroupService)SpringContextUtils.getBean("groupService");
		}
		if(wechatGroupService==null){
			wechatGroupService = (WechatGroupService)SpringContextUtils.getBean("wechatGroupService");
		}
		if(wechatFriendService==null){
			wechatFriendService = (WechatFriendService)SpringContextUtils.getBean("wechatFriendService");
		}
		if(wechatMemberService==null){
			wechatMemberService = (WechatMemberService)SpringContextUtils.getBean("wechatMemberService");
		}
		if(userRelationGroupService==null){
			userRelationGroupService = (UserRelationGroupService)SpringContextUtils.getBean("userRelationGroupService");
		}
		if(redisService==null){
			redisService = (RedisTemplate<String, Object>)SpringContextUtils.getBean("redisTemplate");
		}
	}

	/**
	 * @info 预创建用户
	 * @return
	 */
	@RequestMapping("/signupUser")
	public @ResponseBody ReturnInfo signupUser(){			
		int eventCounts=1500;
		//群成员
		int memberCounts=wechatMemberService.queryByCounts();
		int bCount= memberCounts/eventCounts+1;
        for(int m=0;m<bCount;m++){
        	List<WechatMember> wechatMemberList2 = wechatMemberService.queryWechatMemberByCounts(m*eventCounts,eventCounts);
        	Set<WechatMember> set = new LinkedHashSet<WechatMember>();
        	for(int t=0;t<wechatMemberList2.size();t++){
        		set.add(wechatMemberList2.get(t));
        	}
        	Object obj = redisService.opsForValue().get("newSignupUser");
			if(obj!=null){
				List<String> newSignupUserList = (List<String>) obj;
	        	set.removeAll(newSignupUserList);
        	}
        	List<WechatMember> wechatMemberList = new ArrayList<WechatMember>();
        	wechatMemberList.addAll(set);
        	System.out.println(new Date()+" member:"+m*eventCounts);
			if(wechatMemberList.size()!=0){
				int poolSize=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("myUserCallSize"));
				if(wechatMemberList.size()<poolSize){
					poolSize=wechatMemberList.size();
				}
				//为线程分配数据
				int listSize = (int) Math.ceil((double)wechatMemberList.size()/poolSize);
				List<List<WechatMember>> listFP = new ArrayList<List<WechatMember>>(poolSize);
				for(int j=0;j<poolSize;j++){
					List<WechatMember> tempList = new ArrayList<WechatMember>();
					for(int i=j*listSize;i<(listSize+listSize*j);i++){
						if(i < wechatMemberList.size()){
						   tempList.add(wechatMemberList.get(i));
						}
					}
					listFP.add(tempList);
				}
				ExecutorService executorService= Executors.newFixedThreadPool(poolSize);  
				List<Future<SignupUser>> future = null;
				List<SignupUserMyCallable> myCallableExec = new ArrayList<SignupUserMyCallable>();		
				for(int i=0;i<poolSize;i++){
					myCallableExec.add(new SignupUserMyCallable(listFP.get(i)));
				}
				try {
					future=executorService.invokeAll(myCallableExec);
				} catch (InterruptedException e) {
					logger.error("预处理用户进程执行出现异常！");
				}
				for(Future<SignupUser> futuretemp:future){
					SignupUser temp=null;
					try {
						temp = futuretemp.get();
					} catch (Exception e) {
						logger.error("预处理用户进程获取结果出现异常！"+e);
					} 
					Integer code =(Integer) temp.getMap().get("code");
					if(code==429){
						logger.error("WechatMember读取到id:"+temp.getMap().get("data")+",用户注册已达上限！");
						return ReturnInfo.error("用户注册已达上限！");
					}
				} 
		    }else{
			   System.out.println("用户数据   入库完成!");
			   break;
		    }
	    }
		System.out.println("用户数据   入库完成!");
		SignupUser signupUser = createGroup();
		Integer code = (Integer) signupUser.getMap().get("code");
		if(code==200){
		  System.out.println("群数据   入库完成!");
		  batchGroupsFromPushUser();
		  System.out.println("群成员   入库完成!");
		}else if(code==404){
			return ReturnInfo.error("预创建群，默认群主没有创建（环信）！");
		}else if(code==429){
			return ReturnInfo.error("用户注册已达上限！");
		}else{
			logger.error("预创建群,code: "+code+", msg:"+signupUser.getMap().get("msg"));
		}	
		batchGroupsFromPushUser();
		System.out.println("群成员   入库完成!");
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 预创建群
	 * @return
	 */
	public SignupUser createGroup(){
		String username=PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager");
		List<User> userTemp=userService.queryByUserNameList(username);
		if(userTemp.size()==0){
			//创建默认的群管理员
			RegisterBean registerBean = new RegisterBean();
			registerBean.setUsername(username);
			registerBean.setNickname(username);
			registerBean.setPassword(new Sha256Hash(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.password")).toHex());
			RegisterUsers users = new RegisterUsers();
			com.yishenxiao.commons.beans.User usersingup = new com.yishenxiao.commons.beans.User();
			usersingup.setUsername(registerBean.getUsername());
			usersingup.setPassword(registerBean.getPassword());
			usersingup.setNickname(registerBean.getNickname());
	        users.add(usersingup);
			Object result = easemobIMUsers.createNewIMUserSingle(users);
	        if(result.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	    	   if(easemobErrorInfo.getError_code()==429){
	    		   SignupUser signupUser = new SignupUser();
	    		   Map<String,Object> map = new HashMap<String,Object>();
	    		   map.put("code", 429);
	    		   map.put("msg", "注册名额已达上限!");
	    		   map.put("data", username);
	    		   signupUser.setMap(map);
	    		   return signupUser;
		       }
	    	   logger.error(easemobErrorInfo.getError_code()+"   预注册异常！");
	    	   SignupUser signupUser = new SignupUser();
    		   Map<String,Object> map = new HashMap<String,Object>();
    		   map.put("code", easemobErrorInfo.getError_code());
    		   map.put("msg", "创建群，注册默认群主失败!");
    		   map.put("data", username);
    		   signupUser.setMap(map);
    		   return signupUser;
	        }
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
			user.setUsername(username);
			int c = userService.insertUser(user);
			if(c==1){
				UserSchedule userSchedule = new UserSchedule();
				userSchedule.setCreatetime(new Date());
				String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+"user.png";
				userSchedule.setHeadicon(headicon);
				userSchedule.setMonetary(new BigDecimal(1));
				userSchedule.setNickname(username);
				userSchedule.setPaymentpw("");
				userSchedule.setPaypwone(0);
				userSchedule.setPurseaddress("");
				userSchedule.setUpdatetime(new Date());
				List<User> usertemp = userService.queryByUserNameList(username);
				userSchedule.setUserid(usertemp.get(0).getId());
				int a = userScheduleService.insertUserSchedule(userSchedule);
				if(a==1){
					//这里可以加入redis
					Object obj = redisService.opsForValue().get("newSignupUser");
					if(obj!=null){
						List<String> newSignupUserList = (List<String>) obj;
						newSignupUserList.add(username);
						redisService.opsForValue().set("newSignupUser", newSignupUserList);
					}else{
						List<String> newSignupUserList = new ArrayList<String>();
						newSignupUserList.add(username);
						redisService.opsForValue().set("newSignupUser", newSignupUserList);
					}
				}else{
					List<User> usertemp2 = userService.queryByUserNameList(username);
					int d = userService.deleteById(usertemp2.get(0).getId());
					if(d!=1){
						logger.error("请在数据库手动删除用户, userId: "+usertemp2.get(0).getId());
					}
					//环信删除该用户
			    	Object obj = easemobIMUsers.deleteIMUserByUserName(registerBean.getUsername());
			    	if(obj.toString().contains("error_code")){
		    		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
		    		   if(easemobErrorInfo.getError_code()==404){
		    			  logger.error("删除多余数据，"+registerBean.getUsername()+" 用户不存在！");
		    		   }else{
		    		      logger.error("请在环信删除多余用户，用户:"+registerBean.getUsername()+"  code:"+easemobErrorInfo.getError_code()+"  请联系客服！");
		    		   }
		    		}
				}
			  }else{
					//环信删除该用户
			    	Object obj = easemobIMUsers.deleteIMUserByUserName(username);
			    	if(obj.toString().contains("error_code")){
		    		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
		    		   if(easemobErrorInfo.getError_code()==404){
		    				  logger.error("删除多余数据，"+username+" 用户不存在！");
		    		   }
		    		   logger.error("请在环信删除多余用户，用户:"+username+"  code:"+easemobErrorInfo.getError_code()+"  请联系客服！");
		    		}
			  }
		}
		//建群
		List<WechatGroup> wechatGroupList = wechatGroupService.queryWechatGroupData();
		String groupHead = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.path");
		for(int i=0;i<wechatGroupList.size();i++){
		  if(wechatGroupList.get(i).getMembercount()<20){
			 continue;
		  }
		  List<Group> grouptempList = groupService.queryByGroupDataMD5(wechatGroupList.get(i).getNicknamemd5());//群md5意义不大，name重要
		  if(grouptempList.size()>0){
			  continue;
		  }
		  WechatGroup wechatGroup = wechatGroupList.get(i);
		  EasemobGroupBean easemobGroupBean = new EasemobGroupBean();
		  easemobGroupBean.setGroupName(wechatGroup.getNickname());
		  if(new File(groupHead+wechatGroup.getNicknamemd5()+".jpg").exists()){
			  easemobGroupBean.setGroupIcon(PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+wechatGroup.getNicknamemd5()+".jpg");
		  }else{
			  String imagePath = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+"group.png";
			  easemobGroupBean.setGroupIcon(imagePath);
		  }
		  easemobGroupBean.setUsername(wechatGroup.getUsername());
		  easemobGroupBean.setGroupDesc("");
		  easemobGroupBean.setOwner(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.manager"));
		  Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
		  easemobGroupBean.setMaxusers(groupCount);
		  easemobGroupBean.setApproval(false);
		  easemobGroupBean.setGroupPublic(true);
		  easemobGroupBean.setGroupType(0L);
		  easemobGroupBean.setGroupCount(wechatGroup.getMembercount());
		  easemobGroupBean.setGroupNameCode(wechatGroup.getNicknamemd5());
		  io.swagger.client.model.Group group = new io.swagger.client.model.Group();
          group.groupname(easemobGroupBean.getGroupName()).desc(easemobGroupBean.getGroupDesc())._public(easemobGroupBean.getGroupPublic())
                                 .maxusers(easemobGroupBean.getMaxusers()).approval(easemobGroupBean.getApproval()).owner(easemobGroupBean.getOwner());
          Object result = easemobChatGroup.createChatGroup(group);
          if(result.toString().contains("error_code")){
     	    EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
     	    if(easemobErrorInfo.getError_code()==400){
	     		SignupUser signupUser = new SignupUser();
	     		Map<String,Object> map = new HashMap<String,Object>();
	     		map.put("code", 500);
	     		map.put("msg", "owner不存在！");
	     		signupUser.setMap(map);
	     		return signupUser;
     	    }else{
     		  logger.error(easemobErrorInfo.getError_code()+"   建群异常   请联系客服！");
     	    }
     	    continue;
 	      }
          EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(result.toString(), EasemobSignUpBean.class);
          com.yishenxiao.usermanager.beans.Group sysGroup = new com.yishenxiao.usermanager.beans.Group();
          sysGroup.setEasemobgroupid(easemobSignUpBean.getData().get("groupid").toString());
          sysGroup.setCreatetime(new Date());
          sysGroup.setUsername(easemobGroupBean.getUsername());
          sysGroup.setGroupcategoryid(easemobGroupBean.getGroupType());
          sysGroup.setGroupcount(easemobGroupBean.getGroupCount());
          sysGroup.setGroupnamecode(MD5Utils.getMd5(easemobGroupBean.getGroupName()).toLowerCase());
          sysGroup.setGroupname(easemobGroupBean.getGroupName());
          sysGroup.setGroupowner(easemobGroupBean.getOwner());
          sysGroup.setMolgroup(2);
          sysGroup.setGroupnamecode(easemobGroupBean.getGroupNameCode());
          sysGroup.setRdindex(1);
          sysGroup.setUpdatetime(new Date());
          sysGroup.setGroupicon(easemobGroupBean.getGroupIcon());
          int c = groupService.insertData(sysGroup);
          if(c!=1){
        	  Object obj = easemobChatGroup.deleteChatGroup(sysGroup.getEasemobgroupid()); 
        	  if(obj.toString().contains("error_code")){
        		 logger.error("请在环信手动删除groupid:"+sysGroup.getEasemobgroupid());
        	  }
          }	  
		}
		//处理群名字异常问题
		List<Group> groupList = groupService.queryByData();
		for(int i=0;i<groupList.size();i++){
			Group group = groupList.get(i);
			String groupname=group.getGroupname().replaceAll("[*?]", " ");
			groupService.updateGroupData(group.getId(),"", groupname);
		}
		SignupUser signupUser = new SignupUser();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", 200);
		map.put("msg", "用户数据   入库完成!");
		signupUser.setMap(map);
		return signupUser;
	}
	
	/**
	 * @info 预拉人入群
	 * @return
	 */
	public SignupUser batchGroupsFromPushUser(){
		List<Group> groupList = groupService.queryByRebootData();
		for(int t=0;t<groupList.size();t++){
		   List<WechatMember> wechatMemberList = wechatMemberService.queryByGroupNickNameMD5(groupList.get(t).getGroupnamecode());
			int poolSize=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("myUserGroupCallSize"));
			if(wechatMemberList.size()<poolSize){
				poolSize=wechatMemberList.size();
			}
			//为线程分配数据
			int listSize = (int) Math.ceil((double)wechatMemberList.size()/poolSize);
			List<List<WechatMember>> listFP = new ArrayList<List<WechatMember>>(poolSize);
			for(int j=0;j<poolSize;j++){
				List<WechatMember> tempList = new ArrayList<WechatMember>();
				for(int i=j*listSize;i<(listSize+listSize*j);i++){
					if(i < wechatMemberList.size()){
					   tempList.add(wechatMemberList.get(i));
					}
				}
				listFP.add(tempList);
			}
			ExecutorService executorService= Executors.newFixedThreadPool(poolSize);  
			List<Future<SignupUser>> future = null;
			List<BatchGroupsFromPushUserMyCallable> myCallableExec = new ArrayList<BatchGroupsFromPushUserMyCallable>();		
			for(int i=0;i<poolSize;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group", groupList.get(t));
				map.put("wechatMemberList", listFP.get(i));
				myCallableExec.add(new BatchGroupsFromPushUserMyCallable(map));
			}
			try {
				future=executorService.invokeAll(myCallableExec);
			} catch (InterruptedException e) {
				logger.error("预处理用户进程执行出现异常！");
			}
			for(Future<SignupUser> futuretemp:future){
				SignupUser temp=null;
				try {
					temp = futuretemp.get();
				} catch (Exception e) {
					logger.error("预处理用户进程获取结果出现异常！"+e);
				} 
				Integer code =(Integer) temp.getMap().get("code");
			} 
		}
		SignupUser signupUser = new SignupUser();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", 200);
		map.put("msg", "用户与群数据  入库成功！");
		signupUser.setMap(map);
		return signupUser;
	}
}
