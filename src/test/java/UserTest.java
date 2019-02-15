import io.swagger.client.model.Nickname;
import io.swagger.client.model.UserName;
import io.swagger.client.model.UserNames;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.beans.smsbean.NativeSMS;
import com.yishenxiao.commons.beans.smsbean.SmsVariableResponse;
import com.yishenxiao.commons.service.impl.easemob.EasemobChatGroup;
import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.HttpUtil;
import com.yishenxiao.commons.utils.JiPushUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.easemob.TokenUtil;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.json.Json;

/**
 * Created by easemob on 2017/3/21.
 */
public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
    private static EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
    
    
    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
    
    @Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;

   /* @Test
	public void groupTest() {


	}*/
    @Test
    public void createUser() throws JSONException {
    	/*//请求地址
		String url="http://intapi.253.com/send/json";
		
		//API账号，50位以内。必填
		String account="CI2652257";	
		
		//API账号对应密钥，联系客服获取。必填
		String password="rdRO8tgElZ1d58";
		
		//短信内容。长度不能超过536个字符。必填
		String msg="【币圈】您的验证码是：2530";
		
		//手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
		String mobile="0019143383762";
		
		//组装请求参数
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);
		map.put("msg", msg);
		map.put("mobile", mobile);

		String params=map.toString();
		
		logger.info("请求参数为:" + params);
		try {
			String result=HttpUtil.post(url, params);
			
			logger.info("返回参数为:" + result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("请求异常：" + e);
		}*/ 
    	/*String content="【币圈】您的验证码是："+1222+"，短信有效时间为10分钟。";
		NativeSMS nativeSMS = new NativeSMS();
		nativeSMS.setAccount("CI2652257");
		nativeSMS.setPassword("rdRO8tgElZ1d58");
		nativeSMS.setMsg(content);
		nativeSMS.setMobile("0019143383762");
		SmsVariableResponse smsVariableResponse=null;
		try {
			String result=HttpUtil.post("http://intapi.253.com/send/json", JsonUtils.toJson(nativeSMS));
			smsVariableResponse = (SmsVariableResponse)JSON.parseObject(result, SmsVariableResponse.class);
			System.out.println(smsVariableResponse);
		} catch (Exception e) {
		}*/
    /*	try{
    		UserNames userNames = new UserNames();
            UserName userList = new UserName();
            userList.add("ffc11458aeff9831363f7a7c7b88f458");
            userList.add("f767f279dd6406ad144359cb5352d9d0");
            userNames.usernames(userList);
            Object obj=easemobChatGroup.addBatchUsersToChatGroup("39719890124801", userNames);
            System.out.println(obj);
            }catch(Exception e){
            	e.printStackTrace();
            }*/
    	//部分成员退出群聊
    /*  GroupService groupService = (GroupService) SpringContextUtils.getBean("groupService");
      UserRelationGroupService userRelationGroupService = (UserRelationGroupService) SpringContextUtils.getBean("userRelationGroupService");
      List<Group> groupList =  groupService.queryByData();
      for(int i=0;i<groupList.size();i++){
    	 List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByGroupId(groupList.get(i).getId());
    	 groupService.updateGropCountSet(groupList.get(i).getId(),userRelationGroupList.size());
      }*/
    	/*UserNames userNames = new UserNames();
 	    UserName userList = new UserName();
 	   userList.add("5523f21a2fa31eb300bca94029e65112");
 	    userNames.usernames(userList);
 	    Object result = easemobChatGroup.addBatchUsersToChatGroup("42441721839617", userNames);*/
    	/*Random rad = new Random();
		int temp =rad.nextInt(15);
		if(temp==0){
			temp =15;
		}
		String head="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/"+temp+".png";	
	    System.out.println(head);*/
    	/*Nickname nickname = new Nickname();
		   nickname.setNickname("gess");
		   Object result=easemobIMUsers.modifyIMUserNickNameWithAdminToken("4dfd8971b108969184443f39dbaf76ba", nickname);
		   System.out.println(result.toString());*/
    	new JiPushUtils().sendToAllAndroid("朋友", "你好，欢迎使用", "朋友的酒", "433444");
    	new JiPushUtils().sendToAllIos("朋友", "你好，欢迎使用", "朋友的酒", "433444");
    	System.out.println("12333");
    	
    }

/*    @Test
    public void getUserByName() {
        String userName = "stringa";
        Object result = easemobIMUsers.getIMUserByUserName(userName);
        logger.info(result.toString());
    }

    @Test
    public void gerUsers() {
        Object result = easemobIMUsers.getIMUsersBatch(5L, null);
        logger.info(result.toString());
    }

    @Test
    public void changePassword() {
        String userName = "stringa";
        NewPassword psd = new NewPassword().newpassword("123");
        Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, psd);
        logger.info(result.toString());
    }

    @Test
    public void getFriend() {
        String userName = "stringa";
        Object result = easemobIMUsers.getFriends(userName);
        logger.info(result.toString());
    }*/
    
	
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("groupData")
    public @ResponseBody ReturnInfo groupData(){
		List<UserRelationGroup> userRelationGroupList = userRelationGroupService.queryByGroupId(2238L);
		String[] str={"42441721839617","42441771122689","42441816211457","42441899048962","42441937846273","42442078355458","42442136027138","42442191601668","42442241933313","42442293313538"};
		int z=0;	
		for(int i=20606;i<userRelationGroupList.size();i++){
			String eId=null;
			z=i/1950-1;
		    eId=str[z];	    
		    UserNames userNames = new UserNames();
            UserName userList = new UserName();
            User user = userService.queryByUserIdList(userRelationGroupList.get(i).getUserid()).get(0);
            userList.add(user.getUsername());
            userNames.usernames(userList);
            Object result=easemobChatGroup.addBatchUsersToChatGroup(eId, userNames);
            if(result==null){
            	System.out.println("i: "+i+"   "+user.getUsername());
            	continue;
            }
            //数据入库
            Group group = groupService.queryByeasemobId(eId);
            List<UserRelationGroup> userRelationGrouptemp = userRelationGroupService.queryByDataByList(group.getId(), user.getId());
            if(userRelationGrouptemp.size()==0){
	            groupService.updateGroupCount(eId);
	            UserRelationGroup userRelationGroup = new UserRelationGroup();
	            userRelationGroup.setCreatetime(new Date());
	            userRelationGroup.setDisturbstate(0);
	            userRelationGroup.setGagstate(0);
	            userRelationGroup.setGroupid(group.getId());
	            userRelationGroup.setUpdatetime(new Date());
	            userRelationGroup.setUserid(user.getId());             
	            userRelationGroupService.insertData(userRelationGroup);
	            System.out.println("i: "+i+"   "+"group:"+eId+"   "+"username:"+user.getUsername());
            }
		}
		return ReturnInfo.ok();
	}*/
}
