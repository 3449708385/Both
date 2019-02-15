package com.yishenxiao.commons.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yishenxiao.commons.beans.ProtertiesData;
import com.yishenxiao.commons.beans.ShieldAccount;
import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.beans.easemob.SendInfo;
import com.yishenxiao.commons.beans.postbean.GroupSendDataBean;
import com.yishenxiao.commons.beans.postbean.JPushSendBean;
import com.yishenxiao.commons.controller.EasemobController.Size;
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
import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.JiPushUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.RecordingUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.commons.utils.easemob.Thumb;
import com.yishenxiao.commons.utils.easemob.VideoTimeUtils;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserGagEasemobBean;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.beans.postbean.PullGroupUsers;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("data")
public class DataController {
	
	private static Logger logger = LoggerFactory.getLogger(DataController.class);
	
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
	 * @Info 新人进群推送数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("JPushSend")
    public @ResponseBody ReturnInfo JPushSend(String notificationTitle,String msgTitle,String msgContent,String extrasparam){
		if(StringUtils.judgeBlank(notificationTitle)||StringUtils.judgeBlank(msgContent)){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(msgTitle)){
			msgTitle="defaule";
		}
		if(StringUtils.judgeBlank(extrasparam)){
			extrasparam="defaule";
		}
		JiPushUtils jiPushUtils = new JiPushUtils();
		try{
			//向android 推送消息
			jiPushUtils.sendToAllAndroid(notificationTitle, msgTitle, msgContent, extrasparam);
			//向ios 推送消息
		}catch(Exception e){
			return ReturnInfo.error("推送失败，请重新推送！");
		}
		return ReturnInfo.ok();
	}
	
	
		
	/**
	 * @Info 新人进群推送数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="groupSendData", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo groupSendData(@RequestBody GroupSendDataBean groupSendDataBean){
		String groupIds = groupSendDataBean.getGroupIds();
		if(StringUtils.judgeBlank(groupIds)){
			return ReturnInfo.error("参数错误！");
		}
		/*String[] groups = groupIds.split(",");
		List<Group> listGroup = groupService.queryByArray(groups);
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(new Date());
         calendar.add(Calendar.DATE, -1);
		//先重mongo取数据，取一天前30条数据
		for(int i=0;i<listGroup.size();i++){
			DBCollection  dbCollection  = mongoTemplate.getCollection(listGroup.get(i).getGroupnamecode());
			DBObject dbObject2 = new BasicDBObject();
			dbObject2.put("$gte", calendar.getTime().getTime()/1000);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("Type", "Text");
			dbObject.put("CreateTime", dbObject2);
			Random random = new Random();
			DBCursor dbCursor = dbCollection.find(dbObject).limit(random.nextInt(20)+10);
			while(dbCursor.hasNext()){
				DBObject dbObject3= dbCursor.next();
				try{
				sendInfo(listGroup.get(i).getGroupnamecode(), dbObject3.get("Type").toString(), dbObject3.get("Text").toString(), MD5Utils.getMd5(dbObject3.get("ActualNickName").toString()).toLowerCase(), dbObject3.get("_id").toString());
				}catch(Exception e){}
			}
		}*/
		//
		return ReturnInfo.ok();
	}
	public ReturnInfo sendInfo(String target, String type, String msg, String from, String id){
		    List<SendInfo> sendList = new ArrayList<SendInfo>();
			List<String> groupIdsList = new ArrayList<String>(); 
			List<Group> groupList = groupService.queryByGroupNameCode(target);
			if(groupList.size()==0){
				return ReturnInfo.error("群数据暂未入库！");
			}		
			List<User> userList = userService.queryByUserNameList(from);
			if(userList.size()==0){
				return ReturnInfo.error("用户数据暂未入库！");
			}
			UserSchedule userSchedule = userScheduleService.queryByUIdList(userList.get(0).getId()).get(0);			
			if(type.equals("Text")){		
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("type", "txt");
					map.put("msg", msg);
					groupIdsList.add(groupList.get(0).getEasemobgroupid());
					SendInfo sendInfo = new SendInfo();
					sendInfo.setFrom(from);
					sendInfo.setTarget(groupIdsList);
					sendInfo.setMsg(map);
					if(!StringUtils.judgeBlank(userSchedule.getNickname())){
					  sendInfo.setNickName(userSchedule.getNickname());
					}
					String headIcon = userSchedule.getHeadicon();
					String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
					String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
					sendInfo.setHeadIcon(temp1);
					sendInfo.setTarget_type("chatgroups");
					sendList.add(sendInfo);
					EasemobUtils.EasemobBatchSendInfo(sendList);
			}else if(type.equals("Picture")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				if(result.equals("ok")){	//上传失败不处理
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					if(fileResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "img");
						map.put("url", "https://a1.easemob.com/easemob-demo/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/"+easemobSignUpBean.getEntities().get(0).get("uuid"));
						map.put("filename", fileTempName);
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("size", new Size(480, 720));
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}
				}else{
	                logger.error(msg+"文件上传失败！");				
				}
			}else if(type.equals("Sharing")){
				//文件上传
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("type", "txt");
				map.put("msg", "分享");
				groupIdsList.add(groupList.get(0).getEasemobgroupid());
				SendInfo sendInfo = new SendInfo();
				sendInfo.setFrom(from);
				sendInfo.setTarget(groupIdsList);
				sendInfo.setMsg(map);
				sendInfo.setTarget_type("chatgroups");
				//解析html
				Document doc = Jsoup.parse(msg);
				Map<String,Object> map01 = new HashMap<String,Object>();
				map01.put("title", doc.getElementsByTag("title").text());
				map01.put("message", doc.getElementsByTag("des").text());
				map01.put("url", doc.getElementsByTag("url").text());
				map01.put("thumburl", doc.getElementsByTag("thumburl").text());
				sendInfo.setExt(map01);
				if(!StringUtils.judgeBlank(userSchedule.getNickname())){
					  sendInfo.setNickName(userSchedule.getNickname());
					}
					String headIcon = userSchedule.getHeadicon();
					String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
					String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
					sendInfo.setHeadIcon(temp1);
				sendList.add(sendInfo);
				EasemobUtils.EasemobBatchSendInfo(sendList);
				
			}else if(type.equals("Recording")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				if(result.equals("ok")){	//上传失败不处理
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					if(fileResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "audio");
						map.put("url", "https://a1.easemob.com/easemob-demo/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/"+easemobSignUpBean.getEntities().get(0).get("uuid"));
						map.put("filename", fileTempName);
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("length", RecordingUtils.getDuration(msg));
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}
				}else{
	                logger.error(msg+"文件上传失败！");				
				}
			}else if(type.equals("Video")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				//上传视频
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				//生成缩略图
				String fileTempUrl = msg.substring(0, msg.lastIndexOf("/")+1)+"Thumb";
				File filedir = new File(fileTempUrl);
				if(!filedir.exists()){
					filedir.mkdir();
				}
				String temp = msg.substring(msg.lastIndexOf("/")+1,msg.lastIndexOf("."));
				String filetemp = msg.substring(0, msg.lastIndexOf("/")+1)+"Thumb/"+temp+".jpg";
				try {
					Thumb.fetchFrame(msg, filetemp);
				} catch (Exception e) {
					return ReturnInfo.error(msg + "视频缩略图生成失败！");
				}
				//上传缩略图
				String thumbStatus = new EasemobUtils().EasemobUploadFile(from, filetemp);
				if(result.equals("ok") && thumbStatus.equals("ok")){
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					String userSuffixkey = new Sha256Hash(from+filetemp).toHex();
					Object fileSuffixResult = redisService.opsForValue().get(userSuffixkey);
					if(fileResult!=null && fileSuffixResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    EasemobSignUpBean easemobSignUpBean2 = JsonUtils.jsonToObj(fileSuffixResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "video");
						map.put("filename", fileTempName);
						map.put("thumb", "https://a1.easemob.com/easemob-demo/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/"+easemobSignUpBean2.getEntities().get(0).get("uuid"));
						map.put("length", VideoTimeUtils.getTime(msg));
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("file_length", new File(msg).length());
						map.put("thumb_secret", easemobSignUpBean2.getEntities().get(0).get("share-secret"));
						map.put("url", "https://a1.easemob.com/easemob-demo/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/"+easemobSignUpBean.getEntities().get(0).get("uuid"));																	
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp2 = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp2+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}else{
						return ReturnInfo.ok();
					}
				}else{
					//上传失败
					logger.error(msg+"文件上传失败！");
				}
			}
			return ReturnInfo.ok();
	 }
	
	
	/**
	 * @Info 用户禁言
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("userGag")
    public @ResponseBody ReturnInfo userGag(){
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
		return ReturnInfo.ok();
	}
	
	/**
	 * @Info 禁止用户
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	@RequestMapping(value="prohibitUsers", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo prohibitUsers(@RequestBody PullGroupUsers pullGroupUsers){
		if(StringUtils.judgeBlank(pullGroupUsers.getOtherUserName()) || StringUtils.judgeBlank(pullGroupUsers.getAccount())|| StringUtils.judgeBlank(pullGroupUsers.getUserName())){
			return ReturnInfo.error("参数错误！");
		}
		List<ProtertiesData> protertiesDataList=protertiesDataService.queryByDataKeyList("prohibitUsers");
		if(!protertiesDataList.get(0).getDatavalue().contains(pullGroupUsers.getAccount())){
			return ReturnInfo.error("您没有权限操作！");
		}
		List<Group> groupList= groupService.queryByRealData();
		List<User> user = userService.queryByUserNameList(pullGroupUsers.getUserName());
		List<User> user2 = userService.queryByUserNameList(pullGroupUsers.getOtherUserName());
		if(user2.size()==0){
			return ReturnInfo.error("参数错误！");
		}
		List<ShieldAccount> shieldAccountList=shieldAccountService.queryByPhoneList(user2.get(0).getPhone());
		if(shieldAccountList.size()>0){
			return ReturnInfo.error("已经屏蔽该用户！");
		}
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
		List<MyCallable> myCallableExec = new ArrayList<MyCallable>();		
		for(int i=0;i<poolSize;i++){
			Map<String, Object> myCallMap = new HashMap<String, Object>();
			myCallMap.put("count_group", listFP.get(i).size());
			myCallMap.put("group_list", listFP.get(i));
			myCallMap.put("pullGroupUsers", pullGroupUsers);
			myCallMap.put("user", user);
			myCallMap.put("user2", user2);
			myCallableExec.add(new MyCallable(myCallMap));
		}
		try {
			future=executorService.invokeAll(myCallableExec);
		} catch (InterruptedException e) {
			logger.error("user: "+user2.get(0).getPhone()+" 移除失败！");
		}
		int rcount_group = 0;
		for(Future<Integer> futuretemp:future){
			Integer temp=0;
			try {
				temp = futuretemp.get();
			} catch (Exception e) {
				logger.error("获取禁止用户线程结果出现异常！"+e);
			} 
			rcount_group=rcount_group+temp;
		} 
		//存储广告用户数据
	    ShieldAccount shieldAccount = new ShieldAccount();
	    shieldAccount.setCreatetime(new Date());
	    shieldAccount.setFromuser(user.get(0).getId());
	    shieldAccount.setPhone(user2.get(0).getPhone());
	    shieldAccount.setUpdatetime(new Date());
	    shieldAccountService.insertData(shieldAccount);   
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", groupList.size());
		map.put("count", rcount_group);
		return ReturnInfo.info(200,map);
	}
}	
	
class MyCallable implements Callable<Integer>{
	private static Logger logger = LoggerFactory.getLogger(MyCallable.class);
	private UserRelationGroupService userRelationGroupService;
	private GroupService groupService;
	private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();	
	private Map<String,Object> map = new HashMap<String,Object>();
	public MyCallable(Map<String,Object> map){
		this.map=map;
	}
	
	private void getBean(){
		userRelationGroupService=(UserRelationGroupService) SpringContextUtils.getBean("userRelationGroupService");
		groupService=(GroupService) SpringContextUtils.getBean("groupService");
	}
	
	@Override
	public Integer call() throws Exception {
		getBean();
		int count_group=(int)map.get("count_group");
		List<Group> groupList = (List<Group>) map.get("group_list");
		PullGroupUsers pullGroupUsers = (PullGroupUsers) map.get("pullGroupUsers");
		List<User> user = (List<User>) map.get("user");
		List<User> user2 = (List<User>) map.get("user2");
		for(int i=0;i<groupList.size();i++){
		  //如果这个用户是群主，就不删除
		  if(pullGroupUsers.getOtherUserName().equals(groupList.get(i).getGroupowner())){
			  continue;
	      }
		  //不是群主
		  Object result = easemobChatGroup.addSingleBlockUserToChatGroup(groupList.get(i).getEasemobgroupid(), pullGroupUsers.getOtherUserName());
 	      Thread.sleep(100);
 	      if(result.toString().contains("error_code")){
 	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
 	    	   if(easemobErrorInfo.getError_code()==400){
 	    		  logger.error(pullGroupUsers.getOtherUserName()+"  用户禁用，该用户不存在！");
 	    	   }
 	    	   if(easemobErrorInfo.getError_code()==404){
 	    		  logger.error(groupList.get(i).getEasemobgroupid()+"  用户禁用，该群不存在！");
 	    	   }
 	    	   logger.error(easemobErrorInfo.getError_code()+"  请联系客服！");
 	      }
 	      EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(result.toString(),EasemobSignUpBean.class);
 	      if(!(boolean) easemobSignUpBean.getData().get("result")){
 	    	 count_group--;
 	      }
 	      //删除用户绑定	    
 	      userRelationGroupService.deleteData(groupList.get(i).getId(), user2.get(0).getId());
 	      //修改群人数
 	      groupService.updateGroupInfoData(groupList.get(i).getId(), groupList.get(i).getGroupowner(),groupList.get(i).getGroupcount()-1);
		}
		return count_group;
	}
	
}
