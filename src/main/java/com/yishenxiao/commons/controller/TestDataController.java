package com.yishenxiao.commons.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.easemob.EasemobGroupInfoBean;
import com.yishenxiao.commons.service.DataTestService;
import com.yishenxiao.commons.service.GroupCopyService;
import com.yishenxiao.commons.service.ProtertiesDataService;
import com.yishenxiao.commons.service.ShieldAccountService;
import com.yishenxiao.commons.service.TypeDataService;
import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatGroupService;
import com.yishenxiao.commons.service.WechatMemberService;
import com.yishenxiao.commons.service.impl.easemob.EasemobChatGroup;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("testData")
public class TestDataController {

	private static Logger logger = LoggerFactory.getLogger(TestDataController.class);
	
	@Autowired(required=false)@Qualifier("dataTestService")
	private DataTestService dataTestService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("groupCategoryService")
	private GroupCategoryService groupCategoryService;
	
	@Autowired(required=false)@Qualifier("wechatGroupService")
	private WechatGroupService wechatGroupService;
	
	@Autowired(required=false)@Qualifier("wechatFriendService")
	private WechatFriendService wechatFriendService;
	
	@Autowired(required=false)@Qualifier("wechatMemberService")
	private WechatMemberService wechatMemberService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("userRelationGroupService")
	private UserRelationGroupService userRelationGroupService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisService;
	
	@Autowired(required=false)@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Autowired(required=false)@Qualifier("groupCopyService")
	private GroupCopyService groupCopyService;
	
	@Autowired(required=false)@Qualifier("typeDataService")
	private TypeDataService typeDataService;
	
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
    private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required = false)
	@Qualifier("protertiesDataService")
	private ProtertiesDataService protertiesDataService;
	
	@Autowired(required=false)@Qualifier("shieldAccountService")
	private ShieldAccountService shieldAccountService;
	
	
	/**
	 * @Info 测试
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("groupData")
    public @ResponseBody ReturnInfo groupData(){
		int eventCounts=1000;
		//群成员
		int memberCounts=groupService.queryByRealAndOfficialCount();//查询宝二爷的群与币圈官方群
		int bCount= memberCounts/eventCounts+1;		
		for(int m=0;m<bCount;m++){
			List<Group> groupList = groupService.queryByTempData(m*eventCounts,eventCounts);
			int poolSize=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("myCallSize"));
			//为线程分配数据
			int listSize = (int) Math.ceil((double)groupList.size()/poolSize);
			List<List<Group>> listFP = new ArrayList<List<Group>>(poolSize);
			for(int j=0;j<poolSize;j++){
				List<Group> tempList = new ArrayList<Group>();
				for(int i=j*listSize;i<(listSize+listSize*j);i++){
					if(i < groupList.size()){
					   tempList.add(groupList.get(i));
					}
				}
				listFP.add(tempList);
			}
			ExecutorService executorService= Executors.newFixedThreadPool(poolSize);  
			List<Future<Integer>> future = null;
			List<MyCallabletet> myCallableExec = new ArrayList<MyCallabletet>();		
			for(int i=0;i<poolSize;i++){
				Map<String, Object> myCallMap = new HashMap<String, Object>();
				myCallMap.put("groupList", listFP.get(i));
				myCallableExec.add(new MyCallabletet(myCallMap));
			}
			try {
				future=executorService.invokeAll(myCallableExec);
			} catch (InterruptedException e) {
				logger.error("user: 修改头像线程运行失败！");
			}
			for(Future<Integer> futuretemp:future){
				Integer temp=0;
				try {
					temp = futuretemp.get();
				} catch (Exception e) {
					logger.error("获取线程结果出现异常！"+e);
				} 
				if(temp!=1){
					logger.error("fail");
				}else{
					logger.info("success");
				}
			} 		
		}
		return ReturnInfo.ok();
	}
}


class MyCallabletet implements Callable<Integer>{
	private static Logger logger = LoggerFactory.getLogger(MyCallabletet.class);
	private GroupService groupService;
	private UserService userService;
	private Map<String,Object> map = new HashMap<String,Object>();
	private UserRelationGroupService userRelationGroupService;
	private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	public MyCallabletet(Map<String,Object> map){
		this.map=map;
	}
	
	private void getBean(){
		groupService=(GroupService) SpringContextUtils.getBean("groupService");
		userService=(UserService) SpringContextUtils.getBean("userService");
		userRelationGroupService=(UserRelationGroupService) SpringContextUtils.getBean("userRelationGroupService");
	}
	
	@Override
	public Integer call() throws Exception {
		getBean();
		List<Group> groupList = (List<Group>) map.get("groupList");
        for(int i=0;i<groupList.size();i++){
        	Group group =groupList.get(i);
        	System.out.println("groupname: "+group.getGroupname()+"   groupcode: "+group.getGroupnamecode());
        	String[] groupIds = new String[1];
    		groupIds[0]= group.getEasemobgroupid();
        	Object result = easemobChatGroup.getChatGroupDetails(groupIds);
    		if(result.toString().contains("error_code")){
    	       EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
    	       if(easemobErrorInfo.getError_code()==404){
    	     	  logger.error("您选择的群不存在！");
    		   }else{
    			   logger.error(easemobErrorInfo.getError_code()+"  请联系客服！");
    		   }
    	       continue;
    	    }
    	  EasemobGroupInfoBean easemobGroupInfoBean = JsonUtils.jsonToObj(result.toString(), EasemobGroupInfoBean.class);
    	  List<Map<String,String>> listTemp = (List<Map<String,String>>)easemobGroupInfoBean.getData().get(0).get("affiliations");
    	/*  int c1=groupService.updateGropCountSet(group.getId(), listTemp.size());
    	  if(c1!=1){
				logger.error("groupcount:  "+group.getId()+" update fail");
		  }*/
    	  List<String> userTempList = new ArrayList<String>();
    	  for(int m=0;m<listTemp.size();m++){
    		  Map<String,String> map = listTemp.get(m);		  
    		  userTempList.addAll(map.values());
    	  }
    	  System.out.println("userTempList:"+userTempList);
    	  for(int n=0;n<userTempList.size();n++){
    		List<User> userte = userService.queryByUserNameList(userTempList.get(n));
    		System.out.println("ucount:"+userte.get(0).getId());
    		List<UserRelationGroup> userRelationGroupList =userRelationGroupService.queryByDataByList(group.getId(), userte.get(0).getId());
    		if(userRelationGroupList.size()==0){
    			UserRelationGroup userRelationGroup = new UserRelationGroup();
    			userRelationGroup.setCreatetime(new Date());
    			userRelationGroup.setDisturbstate(0);
    			userRelationGroup.setGagstate(0);
    			userRelationGroup.setGroupid(group.getId());
    			userRelationGroup.setId(userte.get(0).getId());
    			int c=userRelationGroupService.insertData(userRelationGroup);
    			if(c!=1){
    				logger.error(group.getId()+" and  "+ userte.get(0).getId()+" fail");
    			}
    		}
    	  }
        	
        }
		return 1;
	}
	
}
