package com.yishenxiao.usermanager.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.MD5Utils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.extensionUtils;
import com.yishenxiao.commons.utils.easemob.RegisterUsers;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.DistributionRecord;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.DistributionRecordService;
import com.yishenxiao.usermanager.service.LoginService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

import net.sf.json.JSONObject;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userDigitalAddrService")
    private UserDigitalAddrService userDigitalAddrService;
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
    private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("distributionRecordService")
	private DistributionRecordService distributionRecordService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public Map<String, Object> userLogin(Long extensionUserId, String account,String nativecode, String phonecode) {
		   Map<String, Object> map = new HashMap<String, Object>();
		   String username = MD5Utils.getMd5(nativecode+account+UUID.randomUUID().toString()+new Date().getTime());
		   for(int i =0;i <1000;i++){
		     List<User> userList = userService.queryByUserNameList(username);
		     if(userList.size()>0){
		    	 username = MD5Utils.getMd5(nativecode+account+UUID.randomUUID().toString()+new Date().getTime());
		     }else{
		    	 break;
		     }
		   }
		   username = username.toLowerCase();
		   String passwd = new Sha256Hash(UUID.randomUUID().toString()+new Date().getTime()).toHex();
		   
		   //访问环信注册用户
		   RegisterUsers users = new RegisterUsers();
		   com.yishenxiao.commons.beans.User user = new com.yishenxiao.commons.beans.User();
				   user.setUsername(username);
				   user.setPassword(passwd);
				   user.setNickname("");
	       users.add(user);
	       Object obj = easemobIMUsers.createNewIMUserSingle(users);
	       if(obj.toString().contains("error_code")){
	    	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(obj.toString(), EasemobErrorInfo.class);
	    	   if(easemobErrorInfo.getError_code()==400){
	    		   map.put("error_code", "400");
		    	   return map;
	    	   }
	    	   if(easemobErrorInfo.getError_code()==429){
		    	   map.put("error_code", "429");
		    	   return map;
		       }
	    	   map.put("error_code", easemobErrorInfo.getError_code());
	    	   return map;
	       }
	     
		   //用户数据入库
	       User userData = new User();
	       userData.setAccount(account);
	       userData.setCreatetime(new Date());
	       userData.setUsername(username);
	       userData.setIslogin(0);
	       userData.setPasswd(passwd);
	       userData.setPhone(nativecode+account);
	       userData.setState(0);
	       userData.setType(0);
	       userData.setUpdatetime(new Date());
	       userData.setUsertoken(new Sha256Hash(new Date()+nativecode+account+UUID.randomUUID().toString()).toHex());
	       if(extensionUserId!=null){
	    	   userData.setParentextensionid(extensionUserId);
	       }
	       int r_c1=userService.insertUser(userData);
	       if(r_c1!=1){
	    	   map.put("error_code", "500");
	    	   return map;
	       }
	       //根据手机号查询userId用于绑定钱包
	       User userPhone = userService.queryUser(nativecode+account).get(0);
	       //得到配置文件预设币值
	       String extensionOne = PropertiesUtils.getInfoConfigProperties().getProperty("user.extension.one");
	       Random rad = new Random();
		   int temp =rad.nextInt(15);
		   if(temp==0){
			 temp =15;
		   }
		   String headicon="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/"+temp+".png";	
	       //插入注册用户币值
	       UserSchedule userSchedule = new UserSchedule();
	       userSchedule.setMonetary(new BigDecimal(extensionOne));
	       userSchedule.setUserid(userPhone.getId());
	       userSchedule.setCreatetime(new Date());
	       userSchedule.setPaypwone(0);
	       userSchedule.setHeadicon(headicon);
		   userSchedule.setNickname("");
	       userSchedule.setPaymentpw("");
	       userSchedule.setPurseaddress("yishengxiao");
	       userSchedule.setUpdatetime(new Date());
	       int r_c2=userScheduleService.insertUserSchedule(userSchedule);
	       if(r_c2!=1){
	    	   map.put("error_code", "500");
	    	   return map;
	       }
	       map.put("userSignUp", true);
	       map.put("userPhone", userPhone);
	       map.put("userSchedule", userSchedule);
		return map;		
	}
	
	/**
	 * 登陆生成数据
	 * return 0 代表成功
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public int userLoginInfo(Long extensionUserId, String phone, Long userId) {
		 //6层分销计算,更新上级用户的币值
	       String extensionCount = PropertiesUtils.getInfoConfigProperties().getProperty("user.extension.count");
	       String extensionOne = PropertiesUtils.getInfoConfigProperties().getProperty("user.extension.one");
	       int extensionCou=Integer.parseInt(extensionCount);
	       Double tempMonetary=0.0;
	       for(int i=0;i<extensionCou;i++){
	    	   //查找上一层信息
	    	   User parentUser = userService.queryByUserId(extensionUserId);
	    	   if(parentUser==null){
	    		   break;
	    	   }
	    	   //通过上一层数据得到wallet的数据
	    	   UserSchedule parentUserSchedule = userScheduleService.queryByUIdList(parentUser.getId()).get(0);
	    	   //根据算法重新计算数据,七层数据结构重新生成
	    	   //根据uid重新生成数据
	    	   tempMonetary = extensionUtils.getExtensionWallet(extensionUserId);//用于计算用户下七层分销金字塔的数据
	    	   //计算新增的数据
	    	   Double addTempMonetary = tempMonetary-parentUserSchedule.getMonetary().doubleValue();
	    	   parentUserSchedule.setMonetary(new BigDecimal(tempMonetary));
	    	   //更新币值数据
	    	   int r_c=userScheduleService.updateWallet(parentUserSchedule);
	    	   if(r_c!=1){
	    		   return 1;
	    	   }
	    	   DigitalCurrencyInfo digitalCurrencyInfo=digitalCurrencyInfoService.queryByDigitalcurrencyname("MOL");
	    	   int r_c2=userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), parentUser.getId(), addTempMonetary, 0);
	    	   if(r_c2!=1){
	    		   return 1;
	    	   }
	    	   //插入赠送记录
	    	   DistributionRecord distributionRecord = new DistributionRecord();
	    	   distributionRecord.setCreatetime(new Date());
	    	   distributionRecord.setUpdatetime(new Date());
	    	   distributionRecord.setDistributionlevel(i+1);
	    	   distributionRecord.setMolamount(new BigDecimal(addTempMonetary));
	    	   distributionRecord.setNickname("");
	    	   distributionRecord.setPhone(phone);
	    	   distributionRecord.setUserid(parentUser.getId());
	    	   distributionRecord.setFromuserid(userId);
	    	   int r_c1=distributionRecordService.insert(distributionRecord);
	    	   if(r_c1!=1){
	    		   return 1;
	    	   }
	    	   //转向上级用户
	    	   extensionUserId = parentUser.getParentextensionid();
	       }
	       //生成用户ETH地址数据
	       String url = PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"eth/getAddressInEthHDWalletByIndex";
	    		   Map<String, Object> paraterMap = new HashMap<String, Object>();
	    		   paraterMap.put("index", userId);
	       String str =  HttpClientUtils.getSendRequest(url, paraterMap);
	       JSONObject jsonObj = JSONObject.fromObject(str);    
	       UserDigitalAddr userDigitalAddr = new UserDigitalAddr();
	       userDigitalAddr.setCreatetime(new Date());
	       userDigitalAddr.setCurrencyaddr(jsonObj.getString("address"));
	       userDigitalAddr.setUserid(userId);
	       userDigitalAddr.setUpdatetime(new Date());
	       List<DigitalCurrencyInfo> digitalCurrencyInfoList = digitalCurrencyInfoService.queryByCurrencyType("ETH", 0);
	       userDigitalAddr.setDigitalcurrencyinfoid(digitalCurrencyInfoList.get(0).getId());
	       UserDigitalAddr userDigitalAddrtemp = userDigitalAddrService.queryByData(userId, digitalCurrencyInfoList.get(0).getId());
	       if(userDigitalAddrtemp!=null){
	    	   return 2;
	       }
	       int r_c3= userDigitalAddrService.insertData(userDigitalAddr);
	       if(r_c3!=1){
    		   return 1;
    	   }
	       //为每个用户绑定币数据
	       List<DigitalCurrencyInfo> digitalCurrencyInfoDataList = digitalCurrencyInfoService.queryByData();
	       List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList = new ArrayList<UserDigitalCurrencyInfo>();
	       for(int t=0; t<digitalCurrencyInfoDataList.size(); t++){
	    	   UserDigitalCurrencyInfo userDigitalCurrencyInfo = new UserDigitalCurrencyInfo();
	    	   userDigitalCurrencyInfo.setUserid(userId);
	    	   if(digitalCurrencyInfoDataList.get(t).getDigitalcurrencyname().equals("MOL")){
	    	       userDigitalCurrencyInfo.setDigitalcurrencybalance(new BigDecimal(extensionOne));
	    	   }else{
	    		   userDigitalCurrencyInfo.setDigitalcurrencybalance(new BigDecimal(0));
	    	   }
	    	   userDigitalCurrencyInfo.setDigitalcurrencyid(digitalCurrencyInfoDataList.get(t).getId());
	    	   userDigitalCurrencyInfo.setForeignaccountbalance(new BigDecimal(0));
	    	   userDigitalCurrencyInfo.setBorrowedbalance(new BigDecimal(0));
	    	   userDigitalCurrencyInfoList.add(userDigitalCurrencyInfo);
	       }
	       UserDigitalCurrencyInfo userDigitalCurrencyInfo2 = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(1L, userId);
	       if(userDigitalCurrencyInfo2!=null){
	    	   return 2;
	       }
	       int r_c4 = userDigitalCurrencyInfoService.insertList(userDigitalCurrencyInfoList);
	       if(r_c4!=userDigitalCurrencyInfoList.size()){
    		   return 1;
    	   }
	       List<User> usertempList = userService.queryUser(phone);
	       User usertemp=usertempList.get(0);
	       usertemp.setType(1);//状态		
	       int r_c5=userService.updateDate(usertemp);
	       if(r_c5!=1){
    		   return 1;
    	   }
	       return 0;
	}   
}
