package com.yishenxiao.usermanager.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yishenxiao.commons.beans.ProtertiesData;
import com.yishenxiao.commons.beans.easemob.EasemobFriendBean;
import com.yishenxiao.commons.beans.easemob.EasemobGroupInfoBean;
import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.service.ProtertiesDataService;
import com.yishenxiao.commons.service.impl.easemob.EasemobChatGroup;
import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.commons.utils.easemob.TokenUtil;
import com.yishenxiao.usermanager.beans.FirstGroupDataBean;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.GroupCategory;
import com.yishenxiao.usermanager.beans.GroupNew;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserGagEasemobBean;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.beans.postbean.BackGroup;
import com.yishenxiao.usermanager.beans.postbean.CreateGroupBean;
import com.yishenxiao.usermanager.beans.postbean.DeleteEasemobGroupInfoBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserGroupDataBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserGroupMemberDataBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserGroupMembersDataBean;
import com.yishenxiao.usermanager.beans.postbean.PullGroupUsers;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupCountBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupDataBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupHeadBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupInfoBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupRCoefficientBean;
import com.yishenxiao.usermanager.beans.postbean.UploadGroupHeadData;
import com.yishenxiao.usermanager.beans.postbean.UserCreateGroupBean;
import com.yishenxiao.usermanager.beans.postbean.UserInterestRecommendationGroupCategoryBean;
import com.yishenxiao.usermanager.beans.postbean.UserInterestRecommendationGroupCategoryFirstBean;
import com.yishenxiao.usermanager.beans.postbean.UserJoinGroupBean;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.MenuService;
import com.yishenxiao.usermanager.service.RoleService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserRelationRoleService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.NewOwner;
import io.swagger.client.model.UserName;
import io.swagger.client.model.UserNames;

@Controller
@RequestMapping("group")
public class GroupController {
	private static Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("menuService")
    private MenuService menuService;
	
	@Autowired(required=false)@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired(required=false)@Qualifier("userRelationRoleService")
	private UserRelationRoleService userRelationRoleService;
	
	@Autowired(required=false)@Qualifier("groupCategoryService")
	private GroupCategoryService groupCategoryService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userRelationGroupService")
	private UserRelationGroupService userRelationGroupService;
	
	@Autowired(required = false)
	@Qualifier("protertiesDataService")
	private ProtertiesDataService protertiesDataService;
	
	private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	
	private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	
	private static Lock lock = new ReentrantLock();// 锁对象 
	
	/**
	 * @info 修改用户创建群的推荐系数
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupRCoefficient", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo updateGroupRCoefficient(@RequestBody UpdateGroupRCoefficientBean updateGroupRCoefficientBean){
		Long groupId=updateGroupRCoefficientBean.getGroupId();
		Integer rdindex=updateGroupRCoefficientBean.getRdindex();
		if(groupId==null && rdindex==null){
			return ReturnInfo.error("参数错误！");
		}
		Group group = groupService.queryById(groupId);
		if(group==null){
			return ReturnInfo.error("参数错误！");
		}
		groupService.updateGroupRCoefficient(groupId, rdindex);
		return ReturnInfo.ok();	
	}
	
	
	/**
	 * @info 得到群列表
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getGroup", method=RequestMethod.POST, consumes = "application/json")
	public ReturnInfo getGroup(){
		List<Group> groupList = groupService.qureyData();
		return ReturnInfo.info(200, groupList);
	}
	
	/**
	 * @info 用户创建群
	 * @param groupDesc 默认  一声笑默认群备注！
	 * @param groupCount 默认 2000 
	 * @param groupPublic 默认  true
	 * @param approval 默认 false
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	@RequestMapping(value="createGroup", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo createGroup(@RequestBody CreateGroupBean createGroupBean){
		String groupOwnner=createGroupBean.getGroupOwnner();
		String groupName=createGroupBean.getGroupName();
        String groupDesc=createGroupBean.getGroupDesc();
        Integer groupCount=createGroupBean.getGroupCount();
        Boolean groupPublic=createGroupBean.getGroupPublic();
        Boolean approval=createGroupBean.getApproval();
        Long groupType=createGroupBean.getGroupType();
        String membersList=createGroupBean.getMembersList();
		if(StringUtils.judgeBlank(groupOwnner)){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(groupName)){
			return ReturnInfo.error("参数错误！");
		}
		if(groupType==null){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(groupDesc)){
			groupDesc="一声笑默认群备注！";
		}
		if(groupCount==null){
			Integer groupcounttemp=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
			groupCount=groupcounttemp;
		}
		if(groupPublic==null){
			groupPublic=true;
		}
		if(approval==null){
			approval=false;
		}
		if(StringUtils.judgeBlank(createGroupBean.getMembersList())){
			return ReturnInfo.error("参数错误！");
		}
		UserName userName = new UserName();
		String[] members = membersList.split(",");
	    for(int i=0;i<members.length;i++){
	    	userName.add(members[i]);
	    }
	    //成员去重
	    Set<String> set = new  HashSet<String>();
        set.addAll(userName);
        userName.removeAll(userName);
        userName.addAll(set);
		//在环信创建群
		String imagePath =  PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+"group.png";
		io.swagger.client.model.Group group  = new io.swagger.client.model.Group();
        group.groupname(groupName).desc(groupDesc)._public(groupPublic)
                                 .maxusers(groupCount).approval(approval).owner(groupOwnner).members(userName);
        Object result = easemobChatGroup.createChatGroup(group);
        if(result.toString().contains("error_code")){
    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
    	   if(easemobErrorInfo.getError_code()==400){
    		   return ReturnInfo.error("群主不存在！");
    	   }
    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	    }
        EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(result.toString(), EasemobSignUpBean.class);
        //群成员入库
        userName.add(groupOwnner);//把群主加进去
        Set<String> set2 = new  HashSet<String>();
        set2.addAll(userName);
        userName.removeAll(userName);
        userName.addAll(set2);
        //数据入库
        com.yishenxiao.usermanager.beans.Group sysGroup = new com.yishenxiao.usermanager.beans.Group();
        sysGroup.setEasemobgroupid(easemobSignUpBean.getData().get("groupid").toString());
        sysGroup.setCreatetime(new Date());
        sysGroup.setGroupcategoryid(groupType);
        sysGroup.setGroupname(groupName);
        sysGroup.setGroupowner(groupOwnner);
        sysGroup.setGroupdesc(groupDesc);
        sysGroup.setGroupcount(userName.size());
        sysGroup.setRdindex(1);
        sysGroup.setMolgroup(2);
        sysGroup.setUpdatetime(new Date());
        sysGroup.setGroupicon(imagePath);
        sysGroup.setGroupcategoryid(9L);//群类型，默认为9
        int c = groupService.insertData(sysGroup);	
        if(c==1){
           groupService.queryByownr(easemobSignUpBean.getData().get("groupid").toString(), groupOwnner, userName);
        }else{
          Object obj = easemobChatGroup.deleteChatGroup(sysGroup.getEasemobgroupid()); 
       	  if(obj.toString().contains("error_code")){
       		 logger.error("请在环信手动删除groupid:"+sysGroup.getEasemobgroupid());
       	  }
       	  return ReturnInfo.error(500, "创建群失败，请稍后再试！");
        }
		return ReturnInfo.info(200, sysGroup);
	}
	
	/**
	 * @info 修改群组信息
	 * @return 
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateEasemobGroupInfo", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo updateGroupInfo(@RequestBody UpdateGroupInfoBean updateGroupInfoBean){
		String groupId=updateGroupInfoBean.getGroupId();
		String groupDesc=updateGroupInfoBean.getGroupDesc();
		String groupName=updateGroupInfoBean.getGroupName();
	    Integer groupCount=updateGroupInfoBean.getGroupCount();
		if(StringUtils.judgeBlank(groupId)){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(groupDesc) && StringUtils.judgeBlank(groupName) && groupCount==null){
			return ReturnInfo.error("参数错误！");
		}
		ModifyGroup group = new ModifyGroup();
		if(!StringUtils.judgeBlank(groupDesc)){
			group.setDescription(groupDesc);
		}
        if(!StringUtils.judgeBlank(groupName)){
        	group.setGroupname(groupName);
		}
        if(groupCount!=null){
        	group.setMaxusers(groupCount);
		}
        //修改环信的群数据
        Object result = easemobChatGroup.modifyChatGroup(groupId, group);
        if(result.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	    }
        EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(result.toString(), EasemobSignUpBean.class);
        //修改本地的群数据
        int c = groupService.updateGroupData(Long.parseLong(groupId), groupDesc, groupName);
        if(c!=1){
        	logger.error("修改群组数据失败,请检查代码 groupId:"+groupId+"  groupDesc:"+groupDesc+"  groupName:"+groupName);
        	return ReturnInfo.error("修改群组数据失败!");
        }
		return ReturnInfo.info(200, easemobSignUpBean);	
	}
	
	/**
	 * @info 删除群组信息
	 * @return 
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="deleteEasemobGroupInfo", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo deleteEasemobGroupInfo(@RequestBody DeleteEasemobGroupInfoBean deleteEasemobGroupInfoBean){
		String groupId = deleteEasemobGroupInfoBean.getGroupId();
		if(StringUtils.judgeBlank(groupId)){
			return ReturnInfo.error("参数错误！");
		}	
		//删除本地的群数据
        int c=groupService.deleteData(groupId);
        if(c==1){
        	//删除环信的群数据
            Object result = easemobChatGroup.deleteChatGroup(groupId);
            if(result.toString().contains("error_code")){
    	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
    	    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
    	    }
        }else{
        	return ReturnInfo.error("删除群组失败！");	
        }
		return ReturnInfo.ok();	
	}
	
	/**
	 * @info 引导后选择加入群
	 * @param userName 环信用户名
	 * @param easemobIds 环信群id标识
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userJoinGroup", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userJoinGroup(@RequestBody UserJoinGroupBean userJoinGroupBean){
		String userName=userJoinGroupBean.getUserName();
		String easemobIds=userJoinGroupBean.getEasemobIds();
		Long userId=userJoinGroupBean.getUserId();
		if(StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(easemobIds)){
			return ReturnInfo.error("参数错误！");
		}
        String[] easemobIdArr = easemobIds.split(",");
        for(String eId:easemobIdArr){
        	//屏蔽黑名单好友
            Object obj = easemobChatGroup.getChatGroupBlockUsers(eId);
            if(obj.toString().contains("error_code")){
  	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
  	    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
  	        }
    		EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(obj.toString(),EasemobFriendBean.class);
    		List<String> strList = easemobFriendBean.getData();
    		if(strList.contains(userName)){
    			continue;
    		}
    		//判断是不是加入了其他官网群
    		List<Group> groupguanfenglist=groupService.queryByGroupCategoryId(1+"", 10000);			
    		for(int i=0;i<groupguanfenglist.size();i++){
    			Group groupguangfang=groupguanfenglist.get(i);
    			if(groupguangfang.getEasemobgroupid().equals(easemobIds)){
    				for(int m=0;m<groupguanfenglist.size();m++){
    					List<UserRelationGroup> userRelationGroupList =userRelationGroupService.queryByDataByList(groupguangfang.getId(), userId);
    					if(userRelationGroupList.size()>0){
    					  return ReturnInfo.error("您已经加入其他官方群，请不要重复加入！");
    					} 
    				}
    				break;
    			}			
    		}
            Object result=easemobChatGroup.addSingleUserToChatGroup(eId, userName);
            if(result.toString().contains("error_code")){
          	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
          	   if(easemobErrorInfo.getError_code()==403){
          		  return ReturnInfo.error("群人数已满！");
     	       }
          	   if(easemobErrorInfo.getError_code()==400){
          		  return ReturnInfo.error("您的账号异常，请联系客服！");
     	       }
          	   if(easemobErrorInfo.getError_code()==404){
          		  return ReturnInfo.error("您选择的群不存在！");
     	       }
          	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
             }
            //数据入库
            User user = userService.queryByUserNameList(userName).get(0);
            int c=groupService.updateGroupCount(eId);
            if(c!=1){
            	Object result2=easemobChatGroup.removeSingleUserFromChatGroup(eId, userName);
            	if(result2.toString().contains("error_code")){
               	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result2.toString(), EasemobErrorInfo.class);
               	   if(easemobErrorInfo.getError_code()==403){
               		  logger.info("您不在群聊里面！");
          	       }else if(easemobErrorInfo.getError_code()==404){
               		  logger.error("您的账号异常或您选择的群不存在，请联系客服！");
          	       }else{
          	    	  logger.error("引导加群接口, "+userName+"  "+eId+"  "+easemobErrorInfo.getError_code()+" 请查看！");
               	   }
                  }
            	continue;
             }else{
            	 Group group = groupService.queryByeasemobId(eId);
                 UserRelationGroup userRelationGroup = new UserRelationGroup();
                 userRelationGroup.setCreatetime(new Date());
                 userRelationGroup.setDisturbstate(0);
                 userRelationGroup.setGagstate(0);
                 userRelationGroup.setGroupid(group.getId());
                 userRelationGroup.setUpdatetime(new Date());
                 userRelationGroup.setUserid(user.getId());             
                 int i=userRelationGroupService.insertData(userRelationGroup);
                 if(i!=1){
                	int f=userRelationGroupService.deleteData(group.getId(), user.getId());
                	if(f!=1){
                		logger.error("请手动删除该条记录，groupId:"+group.getId()+"  userId:"+user.getId());
                	}
                	int d=groupService.updateGroupCount(eId);
                	if(d!=1){
                		logger.error("请手动为该群人数减一");
                	}
                 	Object result2=easemobChatGroup.removeSingleUserFromChatGroup(eId, userName);
                 	if(result2.toString().contains("error_code")){
                    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result2.toString(), EasemobErrorInfo.class);
                    	   if(easemobErrorInfo.getError_code()==403){
                    		  logger.info("您不在群聊里面！");
               	       }else if(easemobErrorInfo.getError_code()==404){
                    		  logger.error("您的账号异常或您选择的群不存在，请联系客服！");
               	       }else{
               	    	  logger.error(userName+"  "+eId+"  "+easemobErrorInfo.getError_code()+" 请查看！");
                       }
                    }
                 }else{
                     if(!StringUtils.judgeBlank(group.getGroupnamecode())){
                    	List<String> userNameList = new ArrayList<String>();
        	        	UserGagEasemobBean userGagEasemobBean = new UserGagEasemobBean();
        	 			userGagEasemobBean.setMute_duration(-1);
        	 			userNameList.add(userName);
        	 			userGagEasemobBean.setUsernames(userNameList);
        	 			Properties pro = PropertiesUtils.getInfoConfigProperties();
        	 			String str="http://a1.easemob.com/"+pro.getProperty("easemob.appkeymodel")+"/chatgroups/"+eId+"/mute";
        	 	    	String params=JsonUtils.toJson(userGagEasemobBean);
        	 	    	try {
        	 	    		String result3=HttpClientUtils.httpPostWithJSONEasemob(str, params);
        	 				if(result3.contains("error_code")){
        	 	          	   logger.error(userName+"用户加入群聊："+easemobIds+",禁言失败！  error:"+result3);
        	 	            }
        	 			} catch (Exception e) {
        	 				logger.error(userName+"用户加入群聊："+eId+",禁言失败！");
        	 			}
         	    	}
                 }
                 
            }
        }
		return ReturnInfo.ok();	
	}
	
	
	/**
	 * @info 单个用户加一个群
	 * @param userName 环信用户名
	 * @param easemobIds 环信群id标识
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userJoinSingleGroup", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userJoinSingleGroup(@RequestBody UserJoinGroupBean userJoinGroupBean){
		String userName=userJoinGroupBean.getUserName();
		String easemobIds=userJoinGroupBean.getEasemobIds();
		Long userId=userJoinGroupBean.getUserId();
		if(StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(easemobIds)){
			return ReturnInfo.error("参数错误！");
		}
        //屏蔽黑名单好友
        Object obj = easemobChatGroup.getChatGroupBlockUsers(easemobIds);
        if(obj.toString().contains("error_code")){
    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        }
		EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(obj.toString(),EasemobFriendBean.class);
		List<String> strList = easemobFriendBean.getData();
		if(strList.contains(userName)){
			return ReturnInfo.error("您已经被群主加入黑名单，无法加入群聊！");
		}
		//判断是不是加入了其他官网群
		List<Group> groupguanfenglist=groupService.queryByGroupCategoryId(1+"", 10000);			
		for(int i=0;i<groupguanfenglist.size();i++){
			Group groupguangfang=groupguanfenglist.get(i);
			if(groupguangfang.getEasemobgroupid().equals(easemobIds)){
				for(int m=0;m<groupguanfenglist.size();m++){
					List<UserRelationGroup> userRelationGroupList =userRelationGroupService.queryByDataByList(groupguangfang.getId(), userId);
					if(userRelationGroupList.size()>0){
					  return ReturnInfo.error("您已经加入其他官方群，请不要重复加入！");
					} 
				}
				break;
			}			
		}
        Object result=easemobChatGroup.addSingleUserToChatGroup(easemobIds, userName);
        if(result.toString().contains("error_code")){
     	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
     	   if(easemobErrorInfo.getError_code()==403){
     		  return ReturnInfo.error("您已经在群聊里面！");
	       }
     	   if(easemobErrorInfo.getError_code()==400){
     		  return ReturnInfo.error("您的账号异常，请联系客服！");
	       }
     	   if(easemobErrorInfo.getError_code()==404){
     		  return ReturnInfo.error("您选择的群不存在！");
	       }
     	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        }
        //数据入库
        User user = userService.queryByUserNameList(userName).get(0);
        int c=groupService.updateGroupCount(easemobIds);
        if(c!=1){
        	Object result2=easemobChatGroup.removeSingleUserFromChatGroup(easemobIds, userName);
        	if(result2.toString().contains("error_code")){
           	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result2.toString(), EasemobErrorInfo.class);
           	   if(easemobErrorInfo.getError_code()==403){
           		  logger.info("您不在群聊里面！");
      	       }else if(easemobErrorInfo.getError_code()==404){
           		  logger.error("您的账号异常或您选择的群不存在，请联系客服！");
      	       }else{
      	    	  logger.error("引导加群接口, "+userName+"  "+easemobIds+"  "+easemobErrorInfo.getError_code()+" 请查看！");
           	   }
              }
         }else{
	        Group group = groupService.queryByeasemobId(easemobIds);
	        UserRelationGroup userRelationGroup = new UserRelationGroup();
	        userRelationGroup.setCreatetime(new Date());
	        userRelationGroup.setDisturbstate(0);
	        userRelationGroup.setGagstate(0);
	        userRelationGroup.setGroupid(group.getId());
	        userRelationGroup.setUpdatetime(new Date());
	        userRelationGroup.setUserid(user.getId());             
	        int i=userRelationGroupService.insertData(userRelationGroup);
	        if(i!=1){
	        	Object result2=easemobChatGroup.removeSingleUserFromChatGroup(easemobIds, userName);
	        	if(result2.toString().contains("error_code")){
	          	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result2.toString(), EasemobErrorInfo.class);
	          	   if(easemobErrorInfo.getError_code()==403){
	          		  logger.info("您不在群聊里面！");
	     	       }else if(easemobErrorInfo.getError_code()==404){
	          		  logger.error("您的账号异常或您选择的群不存在，请联系客服！");
	     	       }else{
	     	    	  logger.error(userName+"  "+easemobIds+"  "+easemobErrorInfo.getError_code()+" 请查看！");
	          	   }
	             }
	        }else{
		        //禁言
		        if(!StringUtils.judgeBlank(group.getGroupnamecode())){
		        	List<String> userNameList = new ArrayList<String>();
		        	UserGagEasemobBean userGagEasemobBean = new UserGagEasemobBean();
		 			userGagEasemobBean.setMute_duration(-1);
		 			userNameList.add(userName);
		 			userGagEasemobBean.setUsernames(userNameList);
		 			Properties pro = PropertiesUtils.getInfoConfigProperties();
		 			String str="http://a1.easemob.com/"+pro.getProperty("easemob.appkeymodel")+"/chatgroups/"+easemobIds+"/mute";
		 	    	String params=JsonUtils.toJson(userGagEasemobBean);
		 	    	try {
		 				String result3=HttpClientUtils.httpPostWithJSONEasemob(str, params);
		 				if(result3.contains("error_code")){
		 	          	   logger.error(userName+"用户加入群聊："+easemobIds+",禁言失败！  error:"+result3);
		 	            }
		 			} catch (Exception e) {
		 				logger.error(userName+"用户加入群聊："+easemobIds+",禁言失败！");
		 			}
		    	}
	        }
        }       
		return ReturnInfo.ok();	
	}
	
	/**
	 * @Info 用于显示用户是不是在群里面，3个群聊，以及官方群排序，群满不显示
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userInterestRecommendationGroupCategoryFirst", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userInterestRecommendationGroupCategoryFirst(@RequestBody UserInterestRecommendationGroupCategoryFirstBean userInterestRecommendationGroupCategoryFirst){
		if(userInterestRecommendationGroupCategoryFirst.getGroupCount()==0 || userInterestRecommendationGroupCategoryFirst.getGroupCount()==null){
			userInterestRecommendationGroupCategoryFirst.setGroupCount(3);
		}
		Integer retrurn_groupCount=userInterestRecommendationGroupCategoryFirst.getGroupCount();
		List<GroupCategory> groupCategoryList = groupCategoryService.queryByData();
		List<FirstGroupDataBean> firstGroupDataBeanList = new ArrayList<FirstGroupDataBean>();
		for(int i=0;i<groupCategoryList.size();i++){
			GroupCategory groupCategory = groupCategoryList.get(i);
			FirstGroupDataBean firstGroupDataBean = new FirstGroupDataBean();
			firstGroupDataBean.setGroupCategoryId(groupCategory.getId());
			firstGroupDataBean.setGroupCategoryName(groupCategory.getGroupcategoryname());
			List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("groupMembers");
			Integer count=Integer.parseInt(protertiesDataList.get(0).getDatavalue());
			Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
			List<Group> groupList = groupService.queryByGroupCategoryIdCountNew(groupCategory.getId().toString(), retrurn_groupCount, count, groupCount);
			List<GroupNew> groupNewList = new ArrayList<GroupNew>();
			for(int c=0;c<groupList.size();c++){
				Group group = groupList.get(c);
				GroupNew groupNew = new GroupNew();
				groupNew.setCreatetime(group.getCreatetime());
				groupNew.setEasemobgroupid(group.getEasemobgroupid());
				groupNew.setGroupcategoryid(group.getGroupcategoryid());
				groupNew.setGroupcategoryName(group.getGroupcategoryName());
				groupNew.setGroupcount(group.getGroupcount());
				groupNew.setGroupdesc(group.getGroupdesc());
				groupNew.setGroupicon(group.getGroupicon());
				groupNew.setGroupname(group.getGroupname());
				groupNew.setGroupnamecode(group.getGroupnamecode());
				groupNew.setGroupowner(group.getGroupowner());
				groupNew.setId(group.getId());
				groupNew.setMolgroup(group.getMolgroup());
				groupNew.setRdindex(group.getRdindex());
				groupNew.setUpdatetime(group.getUpdatetime());
				groupNew.setUsername(group.getUsername());
				groupNewList.add(groupNew);
			}
			//用户所在群聊判断
			for(int t=0;t<groupNewList.size();t++){
				List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByDataByList(groupNewList.get(t).getId(), userInterestRecommendationGroupCategoryFirst.getUserId());
				if(userRelationGroupList.size()>0){
					groupNewList.get(t).setGroupMemberBoolean(true);
				}else{
					groupNewList.get(t).setGroupMemberBoolean(false);
				}
			}
			firstGroupDataBean.setGroupList(groupNewList);
			firstGroupDataBeanList.add(firstGroupDataBean);
		}	
		return ReturnInfo.info(200, firstGroupDataBeanList);	
	}
	
	/**
	 * @Info 用户获得推荐群数据new,用于显示用户是不是在群里面，全部群聊，以及官方群排序，群满不显示,群人数超过多少人会显示
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userInterestRecommendationGroupCategoryNew", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userInterestRecommendationGroupCategoryNew(@RequestBody UserInterestRecommendationGroupCategoryBean bean){
		List<GroupNew> groups = new ArrayList<GroupNew>();
		if(StringUtils.judgeBlank(bean.getGroupCategoryId())){
			return ReturnInfo.error("参数错误！");
		}
		if(bean.getCou()==null){
			bean.setCou(5);
		}
		List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("groupMembers");
		Integer count=Integer.parseInt(protertiesDataList.get(0).getDatavalue());
		String[]  gCId = bean.getGroupCategoryId().split(",");
		for(int i=0;i<gCId.length;i++){
			Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
			List<Group> groupList = groupService.queryByGroupCategoryIdCountNew(gCId[i], bean.getCou(), count, groupCount);
			if(groupList.size()!=0){
				for(int c=0;c<groupList.size();c++){
					Group group = groupList.get(c);
					GroupNew groupNew = new GroupNew();
					groupNew.setCreatetime(group.getCreatetime());
					groupNew.setEasemobgroupid(group.getEasemobgroupid());
					groupNew.setGroupcategoryid(group.getGroupcategoryid());
					groupNew.setGroupcategoryName(group.getGroupcategoryName());
					groupNew.setGroupcount(group.getGroupcount());
					groupNew.setGroupdesc(group.getGroupdesc());
					groupNew.setGroupicon(group.getGroupicon());
					groupNew.setGroupname(group.getGroupname());
					groupNew.setGroupnamecode(group.getGroupnamecode());
					groupNew.setGroupowner(group.getGroupowner());
					groupNew.setId(group.getId());
					groupNew.setMolgroup(group.getMolgroup());
					groupNew.setRdindex(group.getRdindex());
					groupNew.setUpdatetime(group.getUpdatetime());
					groupNew.setUsername(group.getUsername());
					groups.add(groupNew);
				}
			}	
		}
		if(groups.size()==0){
			return ReturnInfo.error("暂时没有数据！");
		}
		//用户所在群聊判断
		for(int i=0;i<groups.size();i++){
			List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByDataByList(groups.get(i).getId(), bean.getUserId());
			if(userRelationGroupList.size()>0){
				groups.get(i).setGroupMemberBoolean(true);
			}else{
				groups.get(i).setGroupMemberBoolean(false);
			}
		}
		return ReturnInfo.info(200, groups);
	}
	
	/**
	 * @Info 用户感兴趣的群，不显示已经满了的
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userInterestRecommendationGroupCategoryNEW2", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userInterestRecommendationGroupCategoryNEW2(@RequestBody UserInterestRecommendationGroupCategoryBean bean){
		List<Group> groups = new ArrayList<Group>();
		if(StringUtils.judgeBlank(bean.getGroupCategoryId())){
			return ReturnInfo.error("参数错误！");
		}
		if(bean.getCou()==null){
			bean.setCou(5);
		}
		List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("groupMembers");
		Integer count=Integer.parseInt(protertiesDataList.get(0).getDatavalue());
		String[]  gCId = bean.getGroupCategoryId().split(",");
		for(int i=0;i<gCId.length;i++){
			Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
			List<Group> groupList = groupService.queryByGroupCategoryIdCountAstrict(gCId[i], bean.getCou(), count, groupCount);
			if(groupList.size()!=0){
				groups.addAll(groupList);
			}	
		}
		if(groups.size()==0){
			return ReturnInfo.error("暂时没有数据！");
		}
		return ReturnInfo.info(200, groups);
	}
	
	
	/**
	 * @Info 用户获得推荐群数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userInterestRecommendationGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userInterestRecommendationGroupCategory(@RequestBody UserInterestRecommendationGroupCategoryBean bean){
		List<Group> groups = new ArrayList<Group>();
		if(StringUtils.judgeBlank(bean.getGroupCategoryId())){
			return ReturnInfo.error("参数错误！");
		}
		if(bean.getCou()==null){
			bean.setCou(5);
		}
		List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("groupMembers");
		Integer count=Integer.parseInt(protertiesDataList.get(0).getDatavalue());
		String[]  gCId = bean.getGroupCategoryId().split(",");
		for(int i=0;i<gCId.length;i++){
			List<Group> groupList = groupService.queryByGroupCategoryIdCount(gCId[i], bean.getCou(), count);
			if(groupList.size()!=0){
				groups.addAll(groupList);
			}	
		}
		if(groups.size()==0){
			return ReturnInfo.error("暂时没有数据！");
		}
		return ReturnInfo.info(200, groups);
	}
	
	/**
	 * @Info 用户获得用户所在群的群成员头像和昵称
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroupMembersData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroupMembersData(@RequestBody GetUserGroupMembersDataBean getUserGroupMembersDataBean){
		Long groupId=getUserGroupMembersDataBean.getGroupId();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(groupId==null){
			return ReturnInfo.error("参数错误！");
		}
		List<UserRelationGroup> userRelationGroupList2 = userRelationGroupService.queryByGroupId(groupId);
		List<Long> userIds = new ArrayList<Long>();
		for(int i=0;i<userRelationGroupList2.size();i++){
			userIds.add(userRelationGroupList2.get(i).getUserid());
		}
		List<UserSchedule> userList = userScheduleService.queryByUserIdList(userIds);
		for(int i=0;i<userList.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("nickName", userList.get(i).getNickname());
			map.put("headIcon", userList.get(i).getHeadicon());
			list.add(map);
		}
		return ReturnInfo.info(200, list);
	}
	
	/**
	 * @throws IOException   
	 * @Info 用户获得群成员的数据      deleteold wait
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroupMemberData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroupMemberData(@RequestBody GetUserGroupMemberDataBean getUserGroupMemberDataBean){
		String easemobGroupId=getUserGroupMemberDataBean.getEasemobGroupId();
		Integer limit=getUserGroupMemberDataBean.getLimit();
		if(StringUtils.judgeBlank(easemobGroupId)){
			return ReturnInfo.error("参数错误!");
		}
		if(limit==null){
			return ReturnInfo.error("参数错误!");
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String[] groupIds = new String[1];
		groupIds[0]= easemobGroupId;
		Object result = easemobChatGroup.getChatGroupDetails(groupIds);
		if(result.toString().contains("error_code")){
     	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
     	   if(easemobErrorInfo.getError_code()==404){
     		  return ReturnInfo.error("您选择的群不存在！");
	       }else{
     	      return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
     	   }
        }
	    EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(result.toString(), EasemobGroupInfoBean.class);
		List<Map<String,String>> listTemp = (List<Map<String,String>>)easemobGroupInfoBean.getData().get(0).get("affiliations");
	    for(int i=0;i<listTemp.size();i++){
		  Map<String,Object> maptemp = new HashMap<String,Object>();
		  List<User> userList = userService.queryByUserNameList(listTemp.get(i).get("member"));
		  if(userList.size()==0){
			  continue;
		  }
		  User user = userList.get(0);
		  if(user==null){
			  continue;
		  }
		  UserSchedule userSchedule=userScheduleService.queryByUIdList(user.getId()).get(0);
		  maptemp.put("userName", user.getUsername());
		  if(StringUtils.judgeBlank(user.getPhone())){
		     maptemp.put("phone", "");
		  }else{
			 maptemp.put("phone", user.getPhone());
		  }
		  maptemp.put("userId", user.getId());
		  userSchedule.setHeadicon(userSchedule.getHeadicon());
		  maptemp.put("headIcon", userSchedule.getHeadicon());
		  maptemp.put("nickName", userSchedule.getNickname());
		  list.add(maptemp);
		  if(limit==1 && list.size()==10){
			  break;
		  }
		}
	    return ReturnInfo.info(200, list);
	}
	
	
	/**
	 * @throws IOException 
	 * @Info 用户获得群的数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroupData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroupData(@RequestBody GetUserGroupDataBean getUserGroupDataBean){
		String userName=getUserGroupDataBean.getUserName();
		if(StringUtils.judgeBlank(userName)){
			return ReturnInfo.error("参数错误!");
		}
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(60, TimeUnit.SECONDS);  
		client.setWriteTimeout(60, TimeUnit.SECONDS);  
		client.setReadTimeout(60, TimeUnit.SECONDS);   
		Properties pro = PropertiesUtils.getInfoConfigProperties();
		String ORG_NAME = pro.getProperty("easemob.orgname");
    	String APP_NAME = pro.getProperty("easemob.appid");
		Request request = new Request.Builder()
		  .url("https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME+"/users/"+userName+"/joined_chatgroups")
		  .get()
		  .addHeader("authorization", TokenUtil.getAccessToken())
		  .addHeader("content-type", "application/json")
		  .build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			logger.error(userName+" 环信获取该用户群数据异常！");
		}
		String info = null;
		try {
			info = response.body().string();
		} catch (IOException e) {
			logger.error(userName+" 环信获取该用户群数据结果转换异常！");
		}
		if(info==null){
			return ReturnInfo.info(200, "");
		}
		if(info.contains("error_code")){
	       EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(info, EasemobErrorInfo.class);
	       if(easemobErrorInfo.getError_code()==404){
	     	  return ReturnInfo.error("您的账户存在异常，请联系客服！");
		   }
	       return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	    }
		EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(info, EasemobGroupInfoBean.class);
		List<String> easembList = new ArrayList<String>();		
		for(int i=0;i<easemobGroupInfoBean.getData().size();i++){
			easembList.add(easemobGroupInfoBean.getData().get(i).get("groupid").toString());
		}
		List<Group> groupList = new ArrayList<Group>();
		if(easembList.size()!=0){
			groupList = groupService.queryByList(easembList);		
		}
		return ReturnInfo.info(200, groupList);
	}
	
	/**
	 * @throws IOException 
	 * @Info 用户获得群成员的数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroupMemberDataNew", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroupMemberDataNew(@RequestBody GetUserGroupMemberDataBean getUserGroupMemberDataBean){
		Map<String, Object> map = new HashMap<String, Object>();
		String easemobGroupId=getUserGroupMemberDataBean.getEasemobGroupId();
		Integer limit=getUserGroupMemberDataBean.getLimit();
		if(StringUtils.judgeBlank(easemobGroupId)){
			return ReturnInfo.error("参数错误!");
		}
		if(limit==null){
			return ReturnInfo.error("参数错误!");
		}
		Group group=groupService.queryByeasemobId(easemobGroupId);
		if(group==null){
			return ReturnInfo.error("参数错误!");
		}
		GroupCategory groupCategory =groupCategoryService.queryById(group.getGroupcategoryid());
		if(groupCategory==null){
			return ReturnInfo.error("参数错误!");
		}
		group.setGroupcategoryName(groupCategory.getGroupcategoryname());
		map.put("groupData", group);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<User> userGroupOwnerList = userService.queryByUserNameList(group.getGroupowner());
		UserSchedule userGroupOwnerSchedule=userScheduleService.queryByUIdList(userGroupOwnerList.get(0).getId()).get(0);
		Map<String,Object> groupWonnerMap = new HashMap<String,Object>();
		groupWonnerMap.put("userName", userGroupOwnerList.get(0).getUsername());
		if(StringUtils.judgeBlank(userGroupOwnerList.get(0).getPhone())){
		    groupWonnerMap.put("phone", "");
		}else{
			groupWonnerMap.put("phone", userGroupOwnerList.get(0).getPhone());
		}
		groupWonnerMap.put("userId", userGroupOwnerList.get(0).getId());
		groupWonnerMap.put("headIcon", userGroupOwnerSchedule.getHeadicon());
		groupWonnerMap.put("nickName", userGroupOwnerSchedule.getNickname());
		list.add(groupWonnerMap);
		String[] groupIds = new String[1];
		groupIds[0]= easemobGroupId;
		Object result = easemobChatGroup.getChatGroupDetails(groupIds);
		if(result.toString().contains("error_code")){
	       EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	       if(easemobErrorInfo.getError_code()==404){
	     	  return ReturnInfo.error("您选择的群不存在！");
		   }
	       return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	    }
	  EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(result.toString(), EasemobGroupInfoBean.class);
	  List<Map<String,String>> listTemp = (List<Map<String,String>>)easemobGroupInfoBean.getData().get(0).get("affiliations");
	  for(int i=0;i<listTemp.size();i++){
		  Map<String,Object> maptemp = new HashMap<String,Object>();
		  List<User> userList = userService.queryByUserNameList(listTemp.get(i).get("member"));
		  if(userList.size()==0){
			  continue;
		  }
		  User user = userList.get(0);
		  if(user==null){
			  continue;
		  }
		  UserSchedule userSchedule=userScheduleService.queryByUIdList(user.getId()).get(0);
		  maptemp.put("userName", user.getUsername());
		  if(StringUtils.judgeBlank(user.getPhone())){
		     maptemp.put("phone", "");
		  }else{
			 maptemp.put("phone", user.getPhone());
		  }
		  maptemp.put("userId", user.getId());
		  maptemp.put("headIcon", userSchedule.getHeadicon());
		  maptemp.put("nickName", userSchedule.getNickname());
		  list.add(maptemp);
		  if(limit==1 && list.size()==10){
			  break;
		  }
	  }
	  map.put("groupMembers", list);
	  return ReturnInfo.info(200, map);
	}
	
	/**
	 * @Info 用户创建群生成数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userCreateGroup", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userCreateGroup(@RequestBody UserCreateGroupBean userCreateGroupBean){
		String easemobId=userCreateGroupBean.getEasemobId();
		String groupName=userCreateGroupBean.getGroupName();
		String groupOwer=userCreateGroupBean.getGroupOwer(); 
		String groupHead=userCreateGroupBean.getGroupHead();
		String groupDesc=userCreateGroupBean.getGroupDesc();
		Long groupCategoryId=userCreateGroupBean.getGroupCategoryId();
		if(StringUtils.judgeBlank(easemobId) || StringUtils.judgeBlank(groupName) || StringUtils.judgeBlank(groupOwer)){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(groupHead)){
			String filePath=PropertiesUtils.getInfoConfigProperties().getProperty("user.head.path")+"group.png";
			String fileTemp=filePath.substring(filePath.lastIndexOf("/"));
			groupHead =PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath") + fileTemp;
		}
		if(StringUtils.judgeBlank(groupDesc)){
			groupDesc="";
		}
		if(groupCategoryId==null){
			groupCategoryId=2L;
		}
		Group group = new Group();
		group.setCreatetime(new Date());
		group.setEasemobgroupid(easemobId);
		group.setGroupcategoryid(groupCategoryId);
		group.setGroupcount(0);
		group.setGroupdesc(groupDesc);
		group.setGroupicon(groupHead);
		group.setGroupname(groupName);
		group.setGroupowner(groupOwer);
		group.setUpdatetime(new Date());
		group.setUsername("");
		int c = groupService.insertData(group);
		if(c!=1){
			Object obj = easemobChatGroup.deleteChatGroup(easemobId); 
	       	if(obj.toString().contains("error_code")){
	       	  logger.error("请在环信手动删除groupid:"+easemobId);
	       	}
			int d=groupService.deleteData(easemobId);
			if(d!=1){
				logger.error("请手动删除群(本地),环信Id:"+easemobId);
			}
		}
		return ReturnInfo.ok();
	}
	
	/**
	 * @Info 用户修改群头像
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupHead", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo updateGroupHead(@RequestBody UpdateGroupHeadBean updateGroupHeadBean){
		String easemobGroupId=updateGroupHeadBean.getEasemobGroupId();
		String httpGroupHead=updateGroupHeadBean.getHttpGroupHead();
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(httpGroupHead)){
			return ReturnInfo.error("参数错误！");
		}
	    groupService.updateGroupHead(easemobGroupId, httpGroupHead);
		return ReturnInfo.ok();
	}
	
	/**
	 * @Info 用户加群的时候调用,更改群成员个数
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupCount", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo updateGroupCount(@RequestBody UpdateGroupCountBean updateGroupCountBean){
		String easemobGroupId=updateGroupCountBean.getEasemobGroupId();
		if(StringUtils.judgeBlank(easemobGroupId)){
			return ReturnInfo.error("参数错误！");
		}
		groupService.updateGroupCount(easemobGroupId);
		return ReturnInfo.ok();
	}	
	
	/**
	 * @Info 用户加群的时候调用,更改群成员个数以及绑定关联
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo UpdateGroupData(@RequestBody UpdateGroupDataBean updateGroupData){
		if(StringUtils.judgeBlank(updateGroupData.getEasemobGroupIdString()) || updateGroupData.getUserId()==null){
			return ReturnInfo.error("参数错误！");
		}
		String[] easemobIds = updateGroupData.getEasemobGroupIdString().split(",");
		Long userId = updateGroupData.getUserId();	
		User user = userService.queryByUserId(userId);
		if(user==null){
			return ReturnInfo.error("参数错误！");
		}
		for(int i=0;i<easemobIds.length;i++){
			//屏蔽黑名单好友
            Object obj = easemobChatGroup.getChatGroupBlockUsers(easemobIds[i]);
            if(obj.toString().contains("error_code")){
  	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
  	    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
  	        }
    		EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(obj.toString(),EasemobFriendBean.class);
    		List<String> strList = easemobFriendBean.getData();
    		if(strList.contains(user.getUsername())){
    			continue;
    		}
    		//判断是不是加入了其他官网群
    		List<Group> groupguanfenglist=groupService.queryByGroupCategoryId(1+"", 10000);			
    		for(int c=0;c<groupguanfenglist.size();c++){
    			Group groupguangfang=groupguanfenglist.get(c);
    			if(groupguangfang.getEasemobgroupid().equals(easemobIds)){
    				for(int m=0;m<groupguanfenglist.size();m++){
    					List<UserRelationGroup> userRelationGroupList =userRelationGroupService.queryByDataByList(groupguangfang.getId(), userId);
    					if(userRelationGroupList.size()>0){
    					  if(easemobIds.length==1){
    					     return ReturnInfo.error("您已经加入其他官方群，请不要重复加入！");
    					  }
    					} 
    				}
    				break;
    			}			
    		}
			Group group = groupService.queryByeasemobId(easemobIds[i]);
			int c1=groupService.updateGroupCount(easemobIds[i]);
			if(c1==1){
				UserRelationGroup userRelationGroup = new UserRelationGroup();
		        userRelationGroup.setCreatetime(new Date());
		        userRelationGroup.setDisturbstate(0);
		        userRelationGroup.setGagstate(0);
		        userRelationGroup.setGroupid(group.getId());
		        userRelationGroup.setUpdatetime(new Date());
		        userRelationGroup.setUserid(userId);
		        int c=0;
		        try{
		           c=userRelationGroupService.insertData(userRelationGroup);
		        }catch(Exception e){
		        	logger.info("userId:"+updateGroupData.getUserId()+" e:"+e.getMessage());
		        	c=2;
		        	continue;
		        }
		        if(c!=1 && c!=0){
		        	if(easemobIds.length>1){
		        	  logger.error("手动生成数据,userId:"+userRelationGroup.getUserid()+", groupId:"+userRelationGroup.getGroupid());
		        	}else{
		        		return ReturnInfo.error("单个群数据入库失败！");
		        	}
		        }
			}else{
				if(easemobIds.length==1){
		        	return ReturnInfo.error("单个群数据入库失败！");
		        }
			}
	        
		}
		return ReturnInfo.ok();
	}
	
	
	/**
	 * @Info 用户加群的时候调用,更改群成员个数以及开启禁言
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupUser", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo UpdateGroupDataGag(@RequestBody UpdateGroupDataBean updateGroupData){
		if(StringUtils.judgeBlank(updateGroupData.getEasemobGroupIdString()) || updateGroupData.getUserId()==null){
			return ReturnInfo.error("参数错误！");
		}
		String[] easemobIds = updateGroupData.getEasemobGroupIdString().split(",");
		Long userId = updateGroupData.getUserId();	
		User user = userService.queryByUserId(userId);
		if(user==null){
			return ReturnInfo.error("参数错误！");
		}
		for(int i=0;i<easemobIds.length;i++){			
			Group group = groupService.queryByeasemobId(easemobIds[i]);
			if(!StringUtils.judgeBlank(group.getGroupnamecode())){
	        	List<String> userNameList = new ArrayList<String>();
	        	UserGagEasemobBean userGagEasemobBean = new UserGagEasemobBean();
	 			userGagEasemobBean.setMute_duration(-1);
	 			userNameList.add(user.getUsername());
	 			userGagEasemobBean.setUsernames(userNameList);
	 			Properties pro = PropertiesUtils.getInfoConfigProperties();
	 			String str="http://a1.easemob.com/"+pro.getProperty("easemob.appkeymodel")+"/chatgroups/"+easemobIds[i]+"/mute";
	 	    	String params=JsonUtils.toJson(userGagEasemobBean);
	 	    	try {
	 	    		String result3=HttpClientUtils.httpPostWithJSONEasemob(str, params);
	 				if(result3.contains("error_code")){
	 	          	   logger.error(user.getUsername()+"用户加入群聊："+easemobIds+",禁言失败！  error:"+result3);
	 	            }
	 			} catch (Exception e) {
	 				logger.error(user.getUsername()+"用户加入群聊："+easemobIds[i]+",禁言失败！");
	 			}
		    }
		}
		return ReturnInfo.ok();
	}
	
	
	/**
	 * @Info 用户退群
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="backGroup", method=RequestMethod.POST, consumes = "application/json")
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public @ResponseBody ReturnInfo backGroup(@RequestBody BackGroup backGroup){
		String easemobGroupId=backGroup.getEasemobGroupId();
		String userName = backGroup.getUserName();
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(userName)){
			return ReturnInfo.error("参数错误！");
		}
		
		List<User> userList = userService.queryByUserNameList(userName);
		if(userList.size()==0){
			return ReturnInfo.error("参数错误！");
		}
		Group group = groupService.queryByeasemobId(easemobGroupId);
		if(group==null){
			return ReturnInfo.error("参数错误！");
		}
		if(group.getGroupcount()>=3){
			//群转让
			if(group.getGroupowner().equals(userName)){
				List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByOneData(group.getId(), 2);
				Long ownId=0L;
				if(userList.get(0).getId().equals(userRelationGroupList.get(0).getUserid())){
				    ownId=userRelationGroupList.get(1).getUserid();
				}else{
					ownId=userRelationGroupList.get(0).getUserid();
				}
				List<User> usertempList = userService.queryByUserIdList(ownId);
				NewOwner newOwner = new NewOwner();
		        newOwner.newowner(usertempList.get(0).getUsername());
				Object obj = easemobChatGroup.transferChatGroupOwner(easemobGroupId, newOwner);
				if(obj.toString().contains("error_code")){
			       EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
			       return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
			    }
				EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
				if(!(boolean) easemobSignUpBean.getData().get("newowner")){
					return ReturnInfo.error("退出群组失败！");
				}
				group.setGroupowner(usertempList.get(0).getUsername());
			}
			//退群
			Object obj = easemobChatGroup.removeSingleUserFromChatGroup(easemobGroupId, userName);
			if(obj.toString().contains("error_code")){
		       EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
		       if(easemobErrorInfo.getError_code()==403){
				 return ReturnInfo.error("您不在该群里面！");
			   }else if(easemobErrorInfo.getError_code()==404){
			     return ReturnInfo.error("您选择的群不存在或您账户存在异常，请联系管理员！");
			   }
		       return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
			}
			EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
			if(!(boolean) easemobSignUpBean.getData().get("result")){
				return ReturnInfo.error("退出群组失败！");
			}
			
			//人数减一
			group.setGroupcount(group.getGroupcount()-1);
			lock.lock();
			groupService.updateGroupInfoData(group.getId(), group.getGroupowner(), group.getGroupcount());
			//删除关联数据
			userRelationGroupService.deleteData(group.getId(), userList.get(0).getId());
			lock.unlock();
		}else{
			//删除群聊 环信，数据库
			Object obj = easemobChatGroup.deleteChatGroup(easemobGroupId);
			if(obj.toString().contains("error_code")){
			   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
			   logger.error("删除群聊失败："+easemobGroupId+"  "+easemobErrorInfo.getError_code());
			}
			EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
			if(!(boolean) easemobSignUpBean.getData().get("success")){
				return ReturnInfo.error("退出群组失败！");
			}
			lock.lock();
			//删除关联数据
			userRelationGroupService.deleteGroupAllData(group.getId());
			groupService.deleteData(easemobGroupId);
			lock.unlock();
		}
		
		return ReturnInfo.ok();
	}
	
	/**
	 * @Info 用户拉人入群
	 */
	@RequestMapping(value="pullGroupUsers", method=RequestMethod.POST, consumes = "application/json")
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public @ResponseBody ReturnInfo pullGroupUsers(@RequestBody PullGroupUsers pullGroupUsers){
		String easemobGroupId=pullGroupUsers.getEasemobId();
		String userName = pullGroupUsers.getUserNames();
		String owner = pullGroupUsers.getUserName();
		ReturnInfo r=new ReturnInfo();
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(owner)){
			return ReturnInfo.error("参数错误！");
		}
        UserName userList = new UserName();
        String[] nameStr=userName.split(",");
        Group group = groupService.queryByeasemobId(easemobGroupId);
        if(group==null){
        	return ReturnInfo.error("参数错误！");
        }
        for(int i=0;i<nameStr.length;i++){
          userList.add(nameStr[i]);
        }
        //屏蔽黑名单好友
        Object obj = easemobChatGroup.getChatGroupBlockUsers(easemobGroupId);
        if(obj.toString().contains("error_code")){
    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        }
		EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(obj.toString(),EasemobFriendBean.class);
		List<String> strList = easemobFriendBean.getData();
		userList.removeAll(strList);
        //验证所有的用户是否存在
		List<String> strtemplist = new ArrayList<String>();
        for(int i=0;i<userList.size();i++){
         Object obj1 = easemobIMUsers.getIMUserByUserName(userList.get(i));
         if(obj1.toString().contains("error_code")){
      	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj1.toString(), EasemobErrorInfo.class);
      	   if(easemobErrorInfo.getError_code()==404){
      		 strtemplist.add(userList.get(i));
      	   }else{
      		   continue;
      	   }
      	 }
         try {
			Thread.sleep(100);
		 } catch (InterruptedException e) {}
        }
        userList.removeAll(strtemplist);
        int count=0;
        for(String username:userList){
	        Object result = easemobChatGroup.addSingleUserToChatGroup(easemobGroupId, username);
	        if(result.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	    	   if(easemobErrorInfo.getError_code()==400){
		     	  logger.error("您选择的用户未注册！");
		       }
	    	   if(easemobErrorInfo.getError_code()==403){
	     		  logger.error("您选择的用户都在该群！");
	     	   }
	    	   if(easemobErrorInfo.getError_code()==404){
	    		   logger.error("您并未加入该群！");
	    	   }
	    	   logger.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	    	}
			
			//数据关联入库
			List<User> userListtemp = userService.queryByUserNameList(username);
			UserRelationGroup userRelationGroup = new UserRelationGroup();
			userRelationGroup.setCreatetime(new Date());
			userRelationGroup.setDisturbstate(0);
			userRelationGroup.setGagstate(0);
			userRelationGroup.setGroupid(group.getId());
			userRelationGroup.setUpdatetime(new Date());
			userRelationGroup.setUserid(userListtemp.get(0).getId());
		    int c1=userRelationGroupService.insertData(userRelationGroup);
		    if(c1!=1){
		    	Object result2 = easemobChatGroup.removeSingleUserFromChatGroup(easemobGroupId, username);
		    	if(result2.toString().contains("error_code")){
			    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result2.toString(), EasemobErrorInfo.class);			    	
			    	   logger.error(easemobGroupId+" 群，移除用户失败，"+easemobErrorInfo.getError_code()+"  请联系客服！");
			    	}
		    	continue;
		    }else{
		    	count++;
				int c2=groupService.updateGroupCount(easemobGroupId);
				if(c2!=1){
					logger.error(easemobGroupId+" 群人数手动加1");
				}				
		    }
	    }
        r=ReturnInfo.info(200, (userList.size()-count)+" 个用户加入成功，"+count+" 个用户加群失败！");
        //爬数据群群禁言
		if(!StringUtils.judgeBlank(group.getGroupnamecode())){
        	UserGagEasemobBean userGagEasemobBean = new UserGagEasemobBean();
 			userGagEasemobBean.setMute_duration(-1);
 			List<String> userNameList = new ArrayList<String>();
 			for(int q=0;q<nameStr.length;q++){
 				userNameList.add(nameStr[q]);
 			}
 			userGagEasemobBean.setUsernames(userNameList);
 			Properties pro = PropertiesUtils.getInfoConfigProperties();
 			String str="http://a1.easemob.com/"+pro.getProperty("easemob.appkeymodel")+"/chatgroups/"+easemobGroupId+"/mute";
 	    	String params=JsonUtils.toJson(userGagEasemobBean);
 	    	try {
 	    		String result3=HttpClientUtils.httpPostWithJSONEasemob(str, params);
 				if(result3.contains("error_code")){
 	          	   logger.error(userName+"用户加入群聊："+easemobGroupId+",禁言失败！  error:"+result3);
 	            }
 			} catch (Exception e) {
 				logger.error(userName+"用户加入群聊："+group.getEasemobgroupid()+",禁言失败！");
 			}	         
		}
		return r;		
	}
	
	@RequestMapping(value="tirenGroupUsers", method=RequestMethod.POST, consumes = "application/json")
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public @ResponseBody ReturnInfo tirenGroupUsers(@RequestBody PullGroupUsers pullGroupUsers){
		String easemobGroupId=pullGroupUsers.getEasemobId();
		String userName = pullGroupUsers.getUserNames();
		String owner = pullGroupUsers.getUserName();
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(owner)){
			return ReturnInfo.error("参数错误！");
		}
        String[] nameStr=userName.split(",");
        int total = nameStr.length;
        Group group = groupService.queryByeasemobId(easemobGroupId);
        if(group==null){
        	return ReturnInfo.error("参数错误！");
        }
        if(!owner.equals(group.getGroupowner())){
        	return ReturnInfo.error("该用户不是群主！");
        }
        List<String> userList = new ArrayList<String>();
        for(int i=0;i<nameStr.length;i++){
            userList.add(nameStr[i]);
        }
        List<Map<String,Object>> failList = new ArrayList<Map<String,Object>>();
        //踢的人是否包含群主
        List<String> str = new ArrayList<String>();
        for(int i=0;i<nameStr.length;i++){
        	str.add(nameStr[i]);
        }
        if(str.contains(group.getGroupowner())){
        	str.remove(group.getGroupowner());
        }
        nameStr=new String[str.size()];
        nameStr=str.toArray(nameStr);
        if(nameStr.length>1){	        	        
	        Object result = easemobChatGroup.removeBatchUsersFromChatGroup(easemobGroupId, nameStr);
	        if(result.toString().contains("error_code")){
        	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
        	   if(easemobErrorInfo.getError_code()==403){
        		 return ReturnInfo.error("被移除用户不在群组里面！");
        	   }
        	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        	}
	        EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(result.toString(),EasemobGroupInfoBean.class);
	        List<Map<String,Object>> data = easemobGroupInfoBean.getData(); 	                   
	        if(data.size()>0){
	        	for(int i=0;i<data.size();i++){
	        		Map<String,Object> map = data.get(i);
	        		Map<String,Object> maptemp = new HashMap<String,Object>();
	        		if(!(boolean) map.get("result")){       			
	        			//移除失败
	        			userList.remove(map.get("user"));
	        			//失败用户，失败原因
	        			maptemp.put("user", map.get("user"));
	        			maptemp.put("reason", map.get("reason"));
	        			failList.add(maptemp);
	        		}   		
	        	}
	        }
	        //删除关联数据
	        for(int i=0;i<userList.size();i++){
	        	List<User> userList2 = userService.queryByUserNameList(userList.get(i));
	        	userRelationGroupService.deleteData(group.getId(), userList2.get(0).getId());
	        }
        }else{
        	Object obj = easemobChatGroup.removeSingleUserFromChatGroup(easemobGroupId, nameStr[0]);
        	if(obj.toString().contains("error_code")){
    		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
    		   if(easemobErrorInfo.getError_code()==403){
    			   return ReturnInfo.error("被移除用户不在群组里面！");
    		   }
    		   if(easemobErrorInfo.getError_code()==404){
    			   return ReturnInfo.error("没有这个群或您数据存在异常，请联系客服！");
    		   }
    		   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
    		}
        	EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
        	Map<String,Object> maptemp = new HashMap<String,Object>();
        	if(!(boolean) easemobSignUpBean.getData().get("result")){
        		//移除失败
    			userList.remove(easemobSignUpBean.getData().get("user"));
    			//失败用户，失败原因
    			maptemp.put("user", easemobSignUpBean.getData().get("user"));
    			maptemp.put("reason", "未知原因");
    			failList.add(maptemp);
        	}else{
        		List<User> userList2 = userService.queryByUserNameList(nameStr[0]);
        		userRelationGroupService.deleteData(group.getId(), userList2.get(0).getId());
        	}
        } 
        //修改群人数
        groupService.updateGroupInfoData(group.getId(), group.getGroupowner(),group.getGroupcount()-userList.size());
        //返回值
        Map<String, Object> mapObj = new HashMap<String, Object>();
        mapObj.put("total", total);
        mapObj.put("succou", userList.size());
        mapObj.put("faildata", failList);
        if(userList.size()==0){        
          return ReturnInfo.error(500,mapObj);
        }
		return ReturnInfo.info(200,mapObj);	
	}	
	
	/**
	 * 得到群黑名单记录
	 * @param getChatGroupBlockUsers
	 * @return
	 */
	@RequestMapping(value="getChatGroupBlockUsers", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getChatGroupBlockUsers(@RequestBody PullGroupUsers pullGroupUsers){
		String easemobGroupId=pullGroupUsers.getEasemobId();
		if(StringUtils.judgeBlank(easemobGroupId)){
			return ReturnInfo.error("参数错误！");
		}
		Object obj = easemobChatGroup.getChatGroupBlockUsers(easemobGroupId);
		if(obj.toString().contains("error_code")){
    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
    	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        }
		EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(obj.toString(),EasemobFriendBean.class);
		List<String> strList = easemobFriendBean.getData();
		List<Map<String,Object>> str = new ArrayList<Map<String,Object>>();
		for(int i=0;i<strList.size();i++){
			List<User> userList = userService.queryByUserNameList(strList.get(i));
			List<UserSchedule> userScheduleList = userScheduleService.queryByUIdList(userList.get(0).getId());
			Map<String,Object> maptemp = new HashMap<String,Object>();
			maptemp.put("userName", userList.get(0).getUsername());
			maptemp.put("headIcon", userScheduleList.get(0).getHeadicon());
			maptemp.put("nickName", userScheduleList.get(0).getNickname());
			if(StringUtils.judgeBlank(userList.get(0).getPhone())){
		      maptemp.put("phone", "");
		    }else{
			  maptemp.put("phone", userList.get(0).getPhone());
		    }
			maptemp.put("phone", userList.get(0).getPhone());
			maptemp.put("userId", userList.get(0).getId());
			str.add(maptemp);
		}
		return ReturnInfo.info(200, str);		
	}
	
	@RequestMapping(value="addBlockUsers", method=RequestMethod.POST, consumes = "application/json")
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public @ResponseBody ReturnInfo addBlockUsers(@RequestBody PullGroupUsers pullGroupUsers){
		String easemobGroupId=pullGroupUsers.getEasemobId();
		String userName = pullGroupUsers.getUserNames();
		String owner = pullGroupUsers.getUserName();
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(owner)){
			return ReturnInfo.error("参数错误！");
		}
        String[] nameStr=userName.split(",");
        Group group = groupService.queryByeasemobId(easemobGroupId);
        if(group==null){
        	return ReturnInfo.error("参数错误！");
        }
        if(!owner.equals(group.getGroupowner())){
        	return ReturnInfo.error("该用户不是群主！");
        }
        List<String> userList = new ArrayList<String>();
        for(int i=0;i<nameStr.length;i++){
            userList.add(nameStr[i]);
        }
        //踢的人是否包含群主
        if(userList.contains(group.getGroupowner())){
        	userList.remove(group.getGroupowner());
        }
        //验证所有的用户是否存在
  		List<String> strtemplist = new ArrayList<String>();
        for(int i=0;i<userList.size();i++){
	        Object obj1 = easemobIMUsers.getIMUserByUserName(userList.get(i));
	        if(obj1.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj1.toString(), EasemobErrorInfo.class);
	    	   if(easemobErrorInfo.getError_code()==404){
	    		 strtemplist.add(userList.get(i));
	    	   }else{
	    		   continue;
	    	   }
	    	}
	        try {
	  		  Thread.sleep(100);
	  		} catch (InterruptedException e) {}
        }
        userList.removeAll(strtemplist);
        if(userList.size()>1){	       
	        UserNames userNames = new UserNames();
	        UserName userList2 = new UserName();
	        userList2.addAll(userList);
	        userNames.usernames(userList2);
	        Object result = easemobChatGroup.addBatchBlockUsersToChatGroup(easemobGroupId, userNames);
	        if(result.toString().contains("error_code")){
        	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
        	   if(easemobErrorInfo.getError_code()==404){
        		  return ReturnInfo.error("您选择的群不存在！");
        	   }
        	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
        	}        
        }else{
   	        Object result = easemobChatGroup.addSingleBlockUserToChatGroup(easemobGroupId, nameStr[0]);
	   	     if(result.toString().contains("error_code")){
	      	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
	      	   if(easemobErrorInfo.getError_code()==400){
	      		  return ReturnInfo.error("您选择的用户不存在！");
	      	   }
	      	   if(easemobErrorInfo.getError_code()==404){
	      		  return ReturnInfo.error("您选择的群不存在！");
	      	   }
	      	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
	      	}       
        }
        //删除用户绑定
        for(int i=0;i<userList.size();i++){
        	List<User> user2 = userService.queryByUserNameList(userList.get(i));
        	userRelationGroupService.deleteData(group.getId(), user2.get(0).getId());
        }
        //修改群人数
        groupService.updateGroupInfoData(group.getId(), group.getGroupowner(),group.getGroupcount()-userList.size());
		return ReturnInfo.ok();	
	}
	
	@RequestMapping(value="removeBlockUser", method=RequestMethod.POST, consumes = "application/json")
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public @ResponseBody ReturnInfo removeBlockUser(@RequestBody PullGroupUsers pullGroupUsers){
		String easemobGroupId=pullGroupUsers.getEasemobId();
		String userName = pullGroupUsers.getUserNames();
		String owner = pullGroupUsers.getUserName();		
		if(StringUtils.judgeBlank(easemobGroupId) || StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(owner)){
			return ReturnInfo.error("参数错误！");
		}
        String[] nameStr=userName.split(",");
        int total = nameStr.length;
        Group group = groupService.queryByeasemobId(easemobGroupId);
        if(group==null){
        	return ReturnInfo.error("参数错误！");
        }
        if(!owner.equals(group.getGroupowner())){
        	return ReturnInfo.error("该用户不是群主！");
        }
        List<String> userList = new ArrayList<String>();
        for(int i=0;i<nameStr.length;i++){
            userList.add(nameStr[i]);
        }
        //踢的人是否包含群主
        List<String> str = new ArrayList<String>();
        for(int i=0;i<nameStr.length;i++){
        	str.add(nameStr[i]);
        }
        if(str.contains(group.getGroupowner())){
        	str.remove(group.getGroupowner());
        }
        nameStr=new String[str.size()];
        nameStr=str.toArray(nameStr);
        List<Map<String,Object>> failList = new ArrayList<Map<String,Object>>();
        if(nameStr.length>1){
             Object result = easemobChatGroup.removeBatchBlockUsersFromChatGroup(easemobGroupId, nameStr);
             if(result.toString().contains("error_code")){
  	      	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
  	      	   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
  	      	}       
 	        EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(result.toString(),EasemobGroupInfoBean.class);
 	        List<Map<String,Object>> data = easemobGroupInfoBean.getData(); 	                   
             if(data.size()>0){
 	        	for(int i=0;i<data.size();i++){
 	        		Map<String,Object> map = data.get(i);
 	        		Map<String,Object> maptemp = new HashMap<String,Object>();
 	        		if(!(boolean) map.get("result")){       			
 	        			//移除失败
 	        			userList.remove(map.get("user"));
 	        			//失败用户，失败原因
 	        			maptemp.put("user", map.get("user"));
 	        			maptemp.put("reason", map.get("reason"));
 	        			failList.add(maptemp);
 	        		}   		
 	        	}
 	        }
        }else{
        	Object obj = easemobChatGroup.removeSingleBlockUserFromChatGroup(easemobGroupId, nameStr[0]);
        	if(obj.toString().contains("error_code")){
    		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
    		   if(easemobErrorInfo.getError_code()==400){
     			  return ReturnInfo.error("您选择的用户不存在！");
     		   }
    		   if(easemobErrorInfo.getError_code()==404){
    			  return ReturnInfo.error("您选择的群不存在！");
    		   }
    		   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
    		}
        	EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
        	Map<String,Object> maptemp = new HashMap<String,Object>();
        	if(!(boolean) easemobSignUpBean.getData().get("result")){
        		//移除失败
    			userList.remove(easemobSignUpBean.getData().get("user"));
    			//失败用户，失败原因
    			maptemp.put("user", easemobSignUpBean.getData().get("user"));
    			maptemp.put("reason", "未知原因");
    			failList.add(maptemp);
        	}else{
        		List<User> userList2 = userService.queryByUserNameList(nameStr[0]);
        		userRelationGroupService.deleteData(group.getId(), userList2.get(0).getId());
        	}
        }
        //要不要加人
        
        //返回值
        Map<String, Object> mapObj = new HashMap<String, Object>();
        mapObj.put("total", total);
        mapObj.put("succou", userList.size());
        mapObj.put("faildata", failList);
        if(userList.size()==0){        
          return ReturnInfo.error(500,mapObj);
        }
		return ReturnInfo.info(200,mapObj);	
	}
	
	/**
	 * @info 更新头像
	 * @param
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("uploadGroupHeadFile")
	public @ResponseBody ReturnInfo uploadGroupHeadFile(MultipartFile file, String easemobGroupId){	
		ReturnInfo r = new ReturnInfo();
		if(file==null || StringUtils.judgeBlank(easemobGroupId)){
			return ReturnInfo.error("参数错误！");
		}
		String uploadFileName = file.getOriginalFilename();
	    if(uploadFileName.lastIndexOf(".")==-1){
	    	return ReturnInfo.error("该文件不是图片！");
	    }
		String fileSuffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
		if(!fileSuffix.equals(".jpg") && !fileSuffix.equals(".jpeg") && !fileSuffix.equals(".gif") && !fileSuffix.equals(".png") && !fileSuffix.equals(".bmp")){
			return ReturnInfo.error("该文件不是图片！");
		}
		String path = PropertiesUtils.getInfoConfigProperties().getProperty("user.grouphead.path");
		File filetemp = new File(path);
		if(!filetemp.exists()){
			filetemp.mkdirs();
		}
		String filePath = path+UUID.randomUUID().toString()+new Date().getTime()+fileSuffix;
		try {
			file.transferTo(new File(filePath));
			String fileTemp=filePath.substring(filePath.lastIndexOf("/"));
			fileTemp =PropertiesUtils.getInfoConfigProperties().getProperty("info.groupAddr")+"/grouphead" + fileTemp;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("localfile", filePath);
			map.put("httpfile", fileTemp);
			r = ReturnInfo.info(200, map);
		} catch (Exception e) {
			logger.error("服务器保存文件失败！");
			r = ReturnInfo.error();
		}
		return r;
		
	} 
	
	/**
	 * @info 更新群数据
	 * @param
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="uploadGroupHeadData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo uploadGroupHeadData(@RequestBody UploadGroupHeadData uploadGroupHeadData){
		if(StringUtils.judgeBlank(uploadGroupHeadData.getEasemobgroupid())){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(uploadGroupHeadData.getGroupicon()) && StringUtils.judgeBlank(uploadGroupHeadData.getGroupname())){
			return ReturnInfo.error("参数错误！");
		}
		if(!StringUtils.judgeBlank(uploadGroupHeadData.getModel()) && uploadGroupHeadData.getModel().equals("Android")){
		   try {
			   uploadGroupHeadData.setGroupname(URLDecoder.decode(uploadGroupHeadData.getGroupname(),"UTF-8"));
		   } catch (UnsupportedEncodingException e) {
				logger.error("安卓昵称解码失败！");
		   }
		}
		//更新环信群名字
		ModifyGroup group = new ModifyGroup();
		List<Group> groupList = groupService.queryByeasemobIdList(uploadGroupHeadData.getEasemobgroupid());
        String groupId = groupList.get(0).getEasemobgroupid();
        Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
        group.description("一声笑默认群备注！").groupname(uploadGroupHeadData.getGroupname()).maxusers(groupCount);
		Object obj = easemobChatGroup.modifyChatGroup(groupId, group);
		if(obj.toString().contains("error_code")){
		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
		   return ReturnInfo.error(easemobErrorInfo.getError_code()+"  请联系客服！");
		}
		EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(obj.toString(),EasemobSignUpBean.class);
		Map<String,Object> map = easemobSignUpBean.getData();
		if(!(boolean) map.get("groupname")){
			uploadGroupHeadData.setGroupname(groupList.get(0).getGroupname());		
		}
		groupService.updateGroupHeadAndGroupname(uploadGroupHeadData.getEasemobgroupid(), uploadGroupHeadData.getGroupicon(),uploadGroupHeadData.getGroupname());
		return ReturnInfo.ok();	
	}
	
	/**
	 * @info 得到口令群数据
	 * @param
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getGroupLessData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getGroupLessData(@RequestBody PullGroupUsers pullGroupUsers){
		if(StringUtils.judgeBlank(pullGroupUsers.getEasemobId()) || StringUtils.judgeBlank(pullGroupUsers.getUserName())){
			return ReturnInfo.error("参数错误！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Group group = groupService.queryByeasemobId(pullGroupUsers.getEasemobId());
		if(group==null){
			return ReturnInfo.error("该群不存在！");
		}
		map.put("groupName", group.getGroupname());
		map.put("easemobgroupId", group.getEasemobgroupid());
		map.put("groupCount", group.getGroupcount());
		map.put("groupHead", group.getGroupicon());
		List<User> userList = userService.queryByUserNameList(pullGroupUsers.getUserName());
		List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByDataList(group.getId(), userList.get(0).getId());
		if(userRelationGroupList.size()==0){
			map.put("existGroupMember", false);
		}else{
			map.put("existGroupMember", true);
		}
		return ReturnInfo.info(200, map);
	}
}