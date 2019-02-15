package com.yishenxiao.commons.service.impl.quzrtz;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.yishenxiao.commons.beans.RegisterBean;
import com.yishenxiao.commons.beans.SignupUser;
import com.yishenxiao.commons.beans.WechatMember;
import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.easemob.RegisterUsers;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

public class SignupUserMyCallable implements Callable<SignupUser> {

	private static Logger logger = LoggerFactory.getLogger(SignupUserMyCallable.class);
	private List<WechatMember> wechatMemberList;
	private UserService userService;
	private UserScheduleService userScheduleService;
    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
    private RedisTemplate<String,Object> redisService;
	
	private void getBean(){
		userService=(UserService) SpringContextUtils.getBean("userService");
		userScheduleService=(UserScheduleService) SpringContextUtils.getBean("userScheduleService");
		redisService=(RedisTemplate<String,Object>) SpringContextUtils.getBean("redisTemplate");
	}
	
	public SignupUserMyCallable(List<WechatMember> wechatMemberList){
		this.wechatMemberList = wechatMemberList;
	}
	
	@Override
	public SignupUser call() throws Exception {
		getBean();
		for(WechatMember wechatMember:wechatMemberList){
			//判断数据库是否存在这个用户	
			List<User> userTemp=userService.queryByUserNameList(wechatMember.getNicknamemd5());
			if(userTemp.size()>0){
				continue;
			}else{
				RegisterBean registerBean = new RegisterBean();
				registerBean.setUsername(wechatMember.getNicknamemd5());
				registerBean.setNickname(wechatMember.getNickname());
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
		    		   map.put("data", wechatMember.getId());
		    		   signupUser.setMap(map);
		    		   return signupUser;
			       }
		    	   logger.error(easemobErrorInfo.getError_code()+"   预注册异常！");
		    	   continue;
		        }
				//注册用户
				String extensionOne = PropertiesUtils.getInfoConfigProperties().getProperty("user.extension.one");
				User user = new User();
				user.setUsername(wechatMember.getNicknamemd5());
				user.setCreatetime(new Date());
				user.setIslogin(0);
				user.setPasswd(new Sha256Hash(PropertiesUtils.getInfoConfigProperties().getProperty("easemob.default.password")).toHex());
				user.setState(0);
				user.setType(0);
				user.setUpdatetime(new Date());
				int i = userService.insertUser(user);
				if(i==1){
					String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+wechatMember.getNicknamemd5()+".jpg";
					if(!new File(headicon).exists()){
						 Random rad = new Random();
						   int temp =rad.nextInt(15);
						   if(temp==0){
							 temp =15;
						   }
						   headicon="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/"+temp+".png";						       
					}else{
						headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.httpPath")+wechatMember.getNicknamemd5()+".jpg";	
					}
					List<User> tempUser = userService.queryByUserNameList(user.getUsername());
					UserSchedule userSchedule = new UserSchedule();
					userSchedule.setUserid(tempUser.get(0).getId());
					userSchedule.setCreatetime(new Date());
					userSchedule.setNickname(wechatMember.getNickname());
					userSchedule.setPurseaddress("yishengxiao");
					userSchedule.setPaypwone(0);
					userSchedule.setHeadicon(headicon);
					userSchedule.setMonetary(new BigDecimal(extensionOne));
					userSchedule.setPaymentpw("");
					userSchedule.setUpdatetime(new Date());
					int z=userScheduleService.insertUserSchedule(userSchedule);
					if(z==1){
					   //这里可以加入redis
						Object obj = redisService.opsForValue().get("newSignupUser");
						if(obj!=null){
							List<String> newSignupUserList = (List<String>) obj;
							newSignupUserList.add(wechatMember.getNicknamemd5());
							redisService.opsForValue().set("newSignupUser", newSignupUserList);
						}else{
							List<String> newSignupUserList = new ArrayList<String>();
							newSignupUserList.add(wechatMember.getNicknamemd5());
							redisService.opsForValue().set("newSignupUser", newSignupUserList);
						}
					}else{
						int c = userService.deleteById(tempUser.get(0).getId());
						if(c!=1){
							logger.error("请在数据库手动删除用户, userId: "+tempUser.get(0).getId());
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
			    	Object obj = easemobIMUsers.deleteIMUserByUserName(registerBean.getUsername());
			    	if(obj.toString().contains("error_code")){
		    		   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
		    		   if(easemobErrorInfo.getError_code()==404){
		    				  logger.error("删除多余数据，"+registerBean.getUsername()+" 用户不存在！");
		    		   }
		    		   logger.error("请在环信删除多余用户，用户:"+registerBean.getUsername()+"  code:"+easemobErrorInfo.getError_code()+"  请联系客服！");
		    		}
			    }
           }
			//Thread.sleep(1000);
	   }
	   SignupUser signupUser = new SignupUser();
	   Map<String,Object> map = new HashMap<String,Object>();
	   map.put("code", 200);
	   map.put("msg", "线程用户数据   入库完成!");
	   signupUser.setMap(map);
	   return signupUser;
	}

}
