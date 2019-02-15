package com.yishenxiao.usermanager.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import com.yishenxiao.commons.beans.IpAddr;
import com.yishenxiao.commons.beans.ProtertiesData;
import com.yishenxiao.commons.beans.smsbean.NativeSMS;
import com.yishenxiao.commons.beans.smsbean.SmsVariableRequest;
import com.yishenxiao.commons.beans.smsbean.SmsVariableResponse;
import com.yishenxiao.commons.controller.VerificationCodeController;
import com.yishenxiao.commons.service.IpAddrService;
import com.yishenxiao.commons.service.ProtertiesDataService;
import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatMemberService;
import com.yishenxiao.commons.utils.ChuangLanSmsUtil;
import com.yishenxiao.commons.utils.HttpUtil;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.commons.utils.filter.AESUtil;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.NativeCode;
import com.yishenxiao.usermanager.beans.SignUpBean;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.beans.postbean.UserAutoLoginBean;
import com.yishenxiao.usermanager.beans.postbean.UserLogoutBean;
import com.yishenxiao.usermanager.beans.postbean.UserRegisterBean;
import com.yishenxiao.usermanager.beans.postbean.UserWarehousingBean;
import com.yishenxiao.usermanager.service.DistributionRecordService;
import com.yishenxiao.usermanager.service.EasemobInfoService;
import com.yishenxiao.usermanager.service.LoginService;
import com.yishenxiao.usermanager.service.MenuService;
import com.yishenxiao.usermanager.service.NativeCodeService;
import com.yishenxiao.usermanager.service.RoleService;
import com.yishenxiao.usermanager.service.UserRelationRoleService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired(required = false)
	@Qualifier("easemobInfoService")
	private EasemobInfoService easemobInfoService;

	@Autowired(required = false)
	@Qualifier("userService")
	private UserService userService;

	@Autowired(required = false)
	@Qualifier("menuService")
	private MenuService menuService;

	@Autowired(required = false)
	@Qualifier("roleService")
	private RoleService roleService;

	@Autowired(required = false)
	@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;

	@Autowired(required = false)
	@Qualifier("userRelationRoleService")
	private UserRelationRoleService userRelationRoleService;

	@Autowired(required = false)
	@Qualifier("producer")
	private Producer captchaProducer;

	@Autowired(required = false)
	@Qualifier("wechatFriendService")
	private WechatFriendService wechatFriendService;

	@Autowired(required = false)
	@Qualifier("wechatMemberService")
	private WechatMemberService wechatMemberService;

	@Autowired(required = false)
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisService;

	@Autowired(required = false)
	@Qualifier("userDigitalAddrService")
	private UserDigitalAddrService userDigitalAddrService;

	@Autowired(required = false)
	@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;

	@Autowired(required = false)
	@Qualifier("loginService")
	private LoginService loginService;

	@Autowired(required = false)
	@Qualifier("protertiesDataService")
	private ProtertiesDataService protertiesDataService;

	@Autowired(required = false)
	@Qualifier("ipAddrService")
	private IpAddrService ipAddrService;

	@Autowired(required = false)
	@Qualifier("nativeCodeService")
	private NativeCodeService nativeCodeService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("distributionRecordService")
	private DistributionRecordService distributionRecordService;
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * @info 用于注册发送手机验证码
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "userRegister", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userRegister(HttpServletRequest req,
			@RequestBody UserRegisterBean userRegisterBean) {
		String account = userRegisterBean.getAccount();
		String nativecode = userRegisterBean.getNativecode();
		ReturnInfo rInfo = new ReturnInfo();
		// 检查用户
		if (StringUtils.judgeBlank(account)) {
			return ReturnInfo.error("参数错误！");
		}
		
		if (userRegisterBean.getNativecode().equals("0086") && !account.matches("^1[34578]\\d{9}$")) {
			return ReturnInfo.error("参数错误！");
		}
		
		if (StringUtils.judgeBlank(nativecode)) {
			return ReturnInfo.error("参数错误！");
		}
		String phonecode = captchaProducer.createText();
		// 检查内测用户个数
		int userCou = userService.queryByAccountCount();
		List<User> userList = userService.queryUser(nativecode+account);
		if (userList.size() == 0) {
			List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("registerCount");
			if (userCou > Integer.parseInt(protertiesDataList.get(0).getDatavalue())) {
				return ReturnInfo.error("内测名额已达上限，正式版即将上线");
			}
		}
		// 发送短信
		try {
			// 10分钟短信认证频率设置
			Object sessionCode = redisService.opsForValue().get(nativecode + account);
			if (sessionCode != null) {
				return ReturnInfo.error("10分钟以内验证码有效！");
			}
			// ip限制
			String ip = VerificationCodeController.getIpAddr(req);
			List<IpAddr> ipAddrList = ipAddrService.queryByIp(ip);
			if (ipAddrList.size() != 0) {
				List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("ipCount");
				if (ipAddrList.get(0).getCount() >= Integer.parseInt(protertiesDataList.get(0).getDatavalue())) {
					return ReturnInfo.error("该ip地址今日短信数量已达限制！");
				}
				// ip 地址计数加1,并记录ip下的手机号码
				if (!ipAddrList.get(0).getPhone().contains(account)) {
					ipAddrList.get(0).setPhone(ipAddrList.get(0).getPhone() + "," + nativecode + account);
				}
				ipAddrService.updateByCount(ipAddrList.get(0));
			} else {
				IpAddr ipAddr = new IpAddr();
				ipAddr.setCount(1);
				ipAddr.setIpaddr(ip);
				ipAddr.setPhone(nativecode + account);
				ipAddrService.insertData(ipAddr);
			}
			int result = pushDomesticInfo(req, account, phonecode, userRegisterBean.getNativecode());
			if (result == 0) {
				redisService.opsForValue().set(nativecode + account, phonecode, 10, TimeUnit.MINUTES);
				rInfo = ReturnInfo.ok();
			} else {
				rInfo = ReturnInfo.error("短信发送失败！");
			}
		} catch (Exception e) {
			logger.error(nativecode + account + "登陆验证码短信发送失败！");
			rInfo = ReturnInfo.error("登陆验证码短信发送失败");
		}
		return rInfo;
	}

	/**
	 * @info 用于访问连接跳转
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("userRecommend/{extensionUserId}")
	public @ResponseBody ModelAndView userRecommend(@PathVariable("extensionUserId") Long extensionUserId) {
		// modelandview
		ModelAndView mv = new ModelAndView();
		// 1.检查用户携带字段userId
		// 2.判断用户是否存在
		if (extensionUserId == null || userService.queryByUserId(extensionUserId) == null) {
			mv.setViewName("redirect:/err.html");
			return mv;// 这里需要跳转到特定的错误页
		}
		mv.addObject("extensionUserId", extensionUserId);
		mv.setViewName("redirect:/register.html");
		return mv;
	}

	/**
	 * @info 用于后台注册与环信数据注册
	 * @param userId
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NumberFormatException 
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "userWarehousing", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userWarehousing(HttpServletRequest req, HttpServletResponse res,
			@RequestBody UserWarehousingBean userWarehousingBean) throws NumberFormatException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Long extensionUserId = null;
		if(!StringUtils.judgeBlank(userWarehousingBean.getExtensionUserId())){
		   extensionUserId = Long.valueOf(AESUtil.decryptAES(userWarehousingBean.getExtensionUserId(), PropertiesUtils.getInfoConfigProperties().getProperty("ase.pawd")));
		}
		String account = userWarehousingBean.getAccount();
		String nativecode = userWarehousingBean.getNativecode();
		String phonecode = userWarehousingBean.getPhonecode();
		String geetest_challenge = userWarehousingBean.getGeetest_challenge();
		String geetest_validate = userWarehousingBean.getGeetest_validate();
		String geetest_seccode = userWarehousingBean.getGeetest_seccode();
		String type = userWarehousingBean.getType();
		// 检查用户
		if (StringUtils.judgeBlank(account)) {
			return ReturnInfo.error("参数错误！");
		}
		if (StringUtils.judgeBlank(nativecode)) {
			return ReturnInfo.error("参数错误！");
		}
		if (StringUtils.judgeBlank(phonecode)) {
			return ReturnInfo.error("参数错误！");
		}
		if (!StringUtils.judgeBlank(type)) {
			boolean b = false;
			try {
				b = new VerificationCodeController().getCode(req, res, account, type, geetest_challenge,
						geetest_validate, geetest_seccode);
			} catch (Exception e) {
				return ReturnInfo.error("图形验证失败！");
			}
			if (!b) {
				return ReturnInfo.error("图形验证失败！");
			}
		}

		// 检查验证码
		Object sessionCode = redisService.opsForValue().get(nativecode + account);
		if (sessionCode == null) {
			return ReturnInfo.error("手机验证码错误或者已经过期！");
		}
		if (!phonecode.equals(sessionCode.toString())) {
			return ReturnInfo.error("手机验证码错误或者已经过期！");
		} else {
			redisService.opsForValue().set(nativecode + account, "", 1, TimeUnit.SECONDS);
		}

		// 判断手机是否注册
		List<User> userlist = getUser(nativecode + account);
		if (userlist.size() > 1) {
			return ReturnInfo.error();
		}

		if (userlist.size() == 1) {
		   // 拼装数据返回
		   SignUpBean signUpBean = new SignUpBean();
		   User user = userlist.get(0);
		   // 更新数据库
		   if(user.getType()==0){
			   List<DigitalCurrencyInfo> digitalCurrencyInfoList = digitalCurrencyInfoService.queryByCurrencyType("ETH", 0);
			   UserDigitalAddr userDigitalAddrtemp = userDigitalAddrService.queryByData(user.getId(), digitalCurrencyInfoList.get(0).getId());
		       if(userDigitalAddrtemp==null){
		    	   UserDigitalCurrencyInfo userDigitalCurrencyInfo2 = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(1L, user.getId());
			       if(userDigitalCurrencyInfo2==null){
			    	 //正式用户,生成数据
					   int suc = loginService.userLoginInfo(user.getParentextensionid(),user.getPhone(),user.getId());
					   if(suc==1){
							  logger.error(user.getUsername()+"生成数据失败，请联系客服！");
							  return ReturnInfo.error("生成数据失败，请联系客服！");
						   }else if(suc==2){
							   return ReturnInfo.error("请耐心等待数据生成！");
						   }
			       }
		       }   
			}
			// 0：离线；1：在线；2：活跃
			user.setIslogin(1);			
			int c1=userService.updateDate(user);
			if(c1!=1){
				return ReturnInfo.error("登陆失败，请稍后再试！");
			}
			// 32位token使用UUID来表示
			String accessToken = handleToken(user);
			signUpBean.setUser(user);
			signUpBean.setAccessToken(accessToken);
			UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
			userSchedule.setHeadicon(userSchedule.getHeadicon());
			signUpBean.setUserSchedule(userSchedule);
			signUpBean.setUserLogin(false);
			return ReturnInfo.info(200, signUpBean);
		}
		Map<String, Object> map = loginService.userLogin(extensionUserId, account, nativecode, phonecode);
		if(map.get("error_code")!=null){
			if(map.get("error_code").equals("429")){
				return ReturnInfo.error("注册名额已达上限!");
			}else if(map.get("error_code").equals("400")){
				return ReturnInfo.error("请重新注册！");
			}else if(!StringUtils.judgeBlank(map.get("error_code").toString())){
				return ReturnInfo.error(map.get("error_code").toString()+"  请联系客服！");
			}
		}
		// 返回数据到前端
		User userPhone = (User) map.get("userPhone");
		// token
		String accessToken = handleToken(userPhone);
		UserSchedule userSchedule = (UserSchedule) map.get("userSchedule");
		SignUpBean signUpBean = new SignUpBean();
		signUpBean.setUser(userPhone);
		signUpBean.setUserLogin(true);
		signUpBean.setAccessToken(accessToken);
		UserSchedule sigupuserSchedule = userScheduleService.queryByUIdList(userPhone.getId()).get(0);
		sigupuserSchedule.setHeadicon(userSchedule.getHeadicon());
		sigupuserSchedule.setPurseaddress("");
		signUpBean.setUserSchedule(sigupuserSchedule);
		return ReturnInfo.info(200, signUpBean);
	}

	/**
	 * @info 用户自动登录
	 * @param userAutoLoginBean
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "userAutoLogin", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userAutoLogin(@RequestBody UserAutoLoginBean userAutoLoginBean) {
		String userId = userAutoLoginBean.getUserId();
		if (StringUtils.judgeBlank(userId)) {
			return ReturnInfo.error("参数错误！");
		}
		// 修改用户为活跃状态
		User user = userService.queryByUserId(Long.parseLong(userId));
		// 0：离线；1：在线；2：活跃
		user.setIslogin(2);
		userService.updateDate(user);
		return ReturnInfo.info(200, "");
	}

	/**
	 * @info 用户退出
	 * @param userLogoutBean
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "userLogout", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userLogout(@RequestBody UserLogoutBean userLogoutBean) {
		String userId = userLogoutBean.getUserId();
		if (StringUtils.judgeBlank(userId)) {
			return ReturnInfo.error("参数错误！");
		}
		// 修改用户为离线状态
		User user = userService.queryByUserId(Long.parseLong(userId));
		// 0：离线；1：在线；2：活跃
		user.setIslogin(0);
		userService.updateDate(user);
		// 删除redis里面的token
		String accessToken = (String) redisService.opsForValue().get(userId);
		if (!StringUtils.judgeBlank(accessToken)) {
			redisService.delete(accessToken);
		}
		redisService.delete(userId);
		return ReturnInfo.info(200, "");
	}

	private String handleToken(User user) {
		String accessToken = UUID.randomUUID().toString().replace("-", "");
		String oldToken = (String) redisService.opsForValue().get(String.valueOf(user.getId()));
		if (!StringUtils.judgeBlank(oldToken)) {
			redisService.delete(oldToken);
		}
		redisService.opsForValue().set(accessToken, String.valueOf(user.getId()));
		redisService.opsForValue().set(String.valueOf(user.getId()), accessToken);
		return accessToken;
	}

	public int pushDomesticInfo(HttpServletRequest req, String phone, String phonecode, String nativecode) {
		String path = req.getSession().getServletContext().getRealPath("/")+"WEB-INF/classes/SMS.properties";
	    Properties properties = PropertiesUtils.getProperties(path); 
		if("0086".equals(nativecode)){	
			//中国
			String content="【币圈】您的验证码是：{$var}，短信有效时间为10分钟。";
	        SmsVariableRequest smsVariableRequest = new SmsVariableRequest(properties.getProperty("user.cl253"), properties.getProperty("pwd.cl253"),
	                content, phone+","+phonecode, "true", null);
	        String requestJson = JSON.toJSONString(smsVariableRequest);
	        String response = ChuangLanSmsUtil.sendSmsByPost(properties.getProperty("url.cl253"), requestJson);
	        SmsVariableResponse smsVariableResponse = (SmsVariableResponse)JSON.parseObject(response, SmsVariableResponse.class);
	        return Integer.parseInt(smsVariableResponse.getCode());
        }else{
    		String content="【币圈】您的验证码是："+phonecode+"，短信有效时间为10分钟。";
    		NativeSMS nativeSMS = new NativeSMS();
    		nativeSMS.setAccount(properties.getProperty("user.nativecl253"));
    		nativeSMS.setPassword(properties.getProperty("pwd.nativecl253"));
    		nativeSMS.setMsg(content);
    		nativeSMS.setMobile(nativecode+phone);
    		SmsVariableResponse smsVariableResponse=null;
    		try {
    			String result=HttpUtil.post(properties.getProperty("url.nativecl253"), JsonUtils.toJson(nativeSMS));
    			smsVariableResponse = (SmsVariableResponse)JSON.parseObject(result, SmsVariableResponse.class);
    			return Integer.parseInt(smsVariableResponse.getCode());
    		} catch (Exception e) {
    			logger.info("phone:"+nativecode+phone+" "+"异常："+e);
    			return 1;
    		}
        }
	}

	/**
	 * @info 用于后台注册与环信数据注册
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("userJump")
	public @ResponseBody ReturnInfo userJump(Long userId) {
		String url = "http://" + PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")
				+ "spread/personal1.html";
		return ReturnInfo.info(200, url);
	}

	/**
	 * @info 用于后台注册与环信数据注册
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getNativeCode")
	public @ResponseBody ReturnInfo getNativeCode() {
		List<NativeCode> nativeCodeList = nativeCodeService.queryByData();
		for(NativeCode nativeCode:nativeCodeList){
			nativeCode.setNativeCodeNickname("+"+nativeCode.getNativecode().substring(2, nativeCode.getNativecode().length()));
		}
		return ReturnInfo.info(200, nativeCodeList);
	}
	
	// 根据手机查询用户是否存在
	private List<User> getUser(String phone) {
		return userService.queryUser(phone);
	}
}
