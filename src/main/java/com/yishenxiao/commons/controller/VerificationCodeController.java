package com.yishenxiao.commons.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.VerificationCodeBean;
import com.yishenxiao.commons.beans.postbean.GetInstallCodeBean;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.geetestlib.GeetestConfig;
import com.yishenxiao.commons.utils.geetestlib.GeetestLib;

@Controller
@RequestMapping("verificationCode")
public class VerificationCodeController {
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisService;
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="installCode", method=RequestMethod.POST, consumes = "application/json")	
	public @ResponseBody ReturnInfo getInstallCode(HttpServletRequest request, HttpServletResponse response, @RequestBody GetInstallCodeBean getInstallCodeBean){
		String phone=getInstallCodeBean.getPhone();
		String type=getInstallCodeBean.getType();
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
				GeetestConfig.isnewfailback());
		VerificationCodeBean verificationCodeBean=new VerificationCodeBean();
		try{
			//自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>(); 
			param.put("user_id", phone); //网站用户id
			param.put("client_type", type); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", getIpAddr(request)); //传输用户请求验证时所携带的IP
			//进行验证预处理
			int gtServerStatus = gtSdk.preProcess(param);
			//System.out.println("gtServerStatus: "+gtServerStatus);
			//将服务器状态设置到session中
			//System.out.println("1:"+request.getSession().getId());
			//request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
			redisService.opsForValue().set(gtSdk.gtServerStatusSessionKey+phone, gtServerStatus);
			String str = gtSdk.getResponseStr();
			verificationCodeBean.setChallenge(str.substring(str.indexOf("challenge")+12, str.indexOf("gt")-3));
			verificationCodeBean.setGt(str.substring(str.indexOf("gt")+5, str.indexOf("success")-3));
			verificationCodeBean.setSuccess(str.substring(str.indexOf("success")+9, str.lastIndexOf("}")));
			verificationCodeBean.setNew_captcha(true);
		}catch(Exception e){
			return ReturnInfo.error();
		}
		return ReturnInfo.info(200, verificationCodeBean);
	}
	
	/*public static String getIpAddr(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
		  return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
			   return ip.substring(0, index);
			} else {
			   return ip;
			}
		} else {
		   return request.getRemoteAddr();
		}
	}*/
	
	public static String getIpAddr(HttpServletRequest request) {  
		 String ip = request.getHeader("x-forwarded-for");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("WL-Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getRemoteAddr();
         }
         return ip;
    }   
	
	public boolean getCode(HttpServletRequest request, HttpServletResponse response, String phone, String type, String geetest_challenge, String geetest_validate, String geetest_seccode) throws ServletException, IOException {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
				GeetestConfig.isnewfailback());
		try{	
			//从session中获取gt-server状态
			//System.out.println("2:"+request.getSession().getId());
			//int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);		
			int gt_server_status_code = (Integer) getRedisTemplateBean().opsForValue().get(gtSdk.gtServerStatusSessionKey+phone);
			//自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>(); 
			param.put("user_id", phone); //网站用户id
			param.put("client_type", type); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", getIpAddr(request)); //传输用户请求验证时所携带的IP	
			int gtResult = 0;
			if (gt_server_status_code == 1) {
				//gt-server正常，向gt-server进行二次验证	
				gtResult = gtSdk.enhencedValidateRequest(geetest_challenge, geetest_validate, geetest_seccode, param);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				gtResult = gtSdk.failbackValidateRequest(geetest_challenge, geetest_validate, geetest_seccode);
			}	
			if (gtResult == 1) {
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;		
	}
	
	private RedisTemplate<String, Object> getRedisTemplateBean(){
		 return (RedisTemplate<String, Object>)SpringContextUtils.getBean("redisTemplate");
	 }
	
}
