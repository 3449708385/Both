package com.yishenxiao.usermanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.code.kaptcha.Producer;
import com.yishenxiao.commons.beans.DistributionStatisticsBean;
import com.yishenxiao.commons.beans.IpAddr;
import com.yishenxiao.commons.beans.ProtertiesData;
import com.yishenxiao.commons.beans.easemob.EasemobFriend;
import com.yishenxiao.commons.beans.easemob.EasemobFriendBean;
import com.yishenxiao.commons.controller.VerificationCodeController;
import com.yishenxiao.commons.service.IpAddrService;
import com.yishenxiao.commons.service.ProtertiesDataService;
import com.yishenxiao.commons.service.impl.easemob.EasemobIMUsers;
import com.yishenxiao.commons.utils.DateUtils;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.InternationalMobilePhoneNumberUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.commons.utils.filter.AESUtil;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserTransferAccountsDetailsService;
import com.yishenxiao.usermanager.beans.DistributionRecord;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.GroupCategory;
import com.yishenxiao.usermanager.beans.SignUpBean;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserFriendsBean;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.beans.postbean.AddFriendBean;
import com.yishenxiao.usermanager.beans.postbean.DownloadHeadFileBean;
import com.yishenxiao.usermanager.beans.postbean.ForgetToPayThePasswordBean;
import com.yishenxiao.usermanager.beans.postbean.GetBatchUserDataBean;
import com.yishenxiao.usermanager.beans.postbean.GetDistributionCountBean;
import com.yishenxiao.usermanager.beans.postbean.GetPhoneListBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserData;
import com.yishenxiao.usermanager.beans.postbean.GetUserFriendsBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserGroupDataBean;
import com.yishenxiao.usermanager.beans.postbean.GetUserGroupsBean;
import com.yishenxiao.usermanager.beans.postbean.PaymentPasswordCheckBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateNicknameBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateUserInfoBean;
import com.yishenxiao.usermanager.beans.postbean.UpdatepaypwBean;
import com.yishenxiao.usermanager.beans.postbean.UserPhoneCode;
import com.yishenxiao.usermanager.beans.postbean.UserPhoneCodeVerificationBean;
import com.yishenxiao.usermanager.service.DistributionRecordService;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.LoginService;
import com.yishenxiao.usermanager.service.MenuService;
import com.yishenxiao.usermanager.service.RoleService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserRelationRoleService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

import io.swagger.client.model.Nickname;

@Controller
@RequestMapping("user")
public class UserManagerController {
	
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
	
	@Autowired(required=false)@Qualifier("producer")
	private Producer captchaProducer;
	
	@Autowired(required=false)@Qualifier("userRelationGroupService")
	private UserRelationGroupService userRelationGroupService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
    private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("ipAddrService")
	private IpAddrService ipAddrService;
	
	@Autowired(required=false)@Qualifier("distributionRecordService")
	private DistributionRecordService distributionRecordService;
	
	@Autowired(required = false)
	@Qualifier("protertiesDataService")
	private ProtertiesDataService protertiesDataService;
	
	@Autowired(required=false)@Qualifier("userDigitalAddrService")
    private UserDigitalAddrService userDigitalAddrService;
	
	@Autowired(required=false)@Qualifier("userTransferAccountsDetailsService")
	private UserTransferAccountsDetailsService userTransferAccountsDetailsService;
	
	@Autowired(required = false)
	@Qualifier("loginService")
	private LoginService loginService;
	
	private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	
	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
	
	/**
	 * @info 得到用户分销成果数据
	 * @param userId
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getDistributionCount", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getDistributionCount(@RequestBody GetDistributionCountBean getDistributionCountBean) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		Long userId = getDistributionCountBean.getUserId();
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		DistributionStatisticsBean distributionStatisticsBean = new DistributionStatisticsBean();
		List<Integer> listTemp = new ArrayList<Integer>();
		List<Long> list = new ArrayList<Long>();
		for(int i=0;i<6;i++){
			List<Long> list2 = new ArrayList<Long>();
			list2.addAll(list);
			list = new ArrayList<Long>();
			if(i==0){
			   List<User> userList = userService.queryByExtensionUserId(userId);
			   if(userList.size()>0){ 
			      listTemp.add(userList.size());
			      for(User user:userList){
			    	  list.add(user.getId());
				  }
			   }else{
				   listTemp.add(0);
				   break;
			   }
			}else{
				int cou =0;
				for(Long temp:list2){
					List<User> userList = userService.queryByExtensionUserId(temp);
					cou=cou+userList.size();
					for(User user:userList){
				    	  list.add(user.getId());
					}
				}
				listTemp.add(cou);
			}	
		}
		if(listTemp.size()>1){
			distributionStatisticsBean.setM1(listTemp.get(0));
			distributionStatisticsBean.setM2(listTemp.get(1));
			distributionStatisticsBean.setM3(listTemp.get(2));
			distributionStatisticsBean.setM4(listTemp.get(3));
			distributionStatisticsBean.setM5(listTemp.get(4));
			distributionStatisticsBean.setM6(listTemp.get(5));
		}else{
			distributionStatisticsBean.setM1(listTemp.get(0));
			distributionStatisticsBean.setM2(listTemp.get(0));
			distributionStatisticsBean.setM3(listTemp.get(0));
			distributionStatisticsBean.setM4(listTemp.get(0));
			distributionStatisticsBean.setM5(listTemp.get(0));
			distributionStatisticsBean.setM6(listTemp.get(0));
		}
		User user = userService.queryByUserId(userId);
		distributionStatisticsBean.setInviteCode(user.getId().toString());
		DigitalCurrencyInfo digitalCurrencyInfo=digitalCurrencyInfoService.queryByDigitalcurrencyname("MOL");
		//获取eth外账的数据  内账+外借+余额差值
		List<UserDigitalCurrencyInfo> userDigitalCurrencyList = userDigitalCurrencyInfoService.queryByDigitalCurrencyIdList(digitalCurrencyInfo.getId(), userId);
		try{
		  distributionStatisticsBean.setBothPoint(userDigitalCurrencyList.get(0).getDigitalcurrencybalance().toPlainString());
		}catch(Exception e){
			System.out.println("getDistributionCount:"+userId);
		}
		distributionStatisticsBean.setCreated(new Date().getTime());
	    String userlink = "https://www.both.im/register.html?extensionUserId="+AESUtil.encryptAES(userId.toString(), PropertiesUtils.getInfoConfigProperties().getProperty("ase.pawd"));
		distributionStatisticsBean.setInviteUrl(userlink);
		String buffer ="【币圈】我在用币圈，100多万币友、3000多个币圈群等你来加入，你也来试试："+userlink+"? 注册就送100mol币，邀请好友还有更多奖励噢 🍭了解摩尔链信息请点击：https://www.mol.one";
		distributionStatisticsBean.setInviteDoc(buffer.toString());
		return ReturnInfo.info(200, distributionStatisticsBean); 
	}
	
	/**
	 * @info 得到用户分销成果数据_改进版
	 * @param userId
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getDistribution", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getDistribution(@RequestBody GetDistributionCountBean getDistributionCountBean) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{		
		Long userId = getDistributionCountBean.getUserId();
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		List<Integer> listTemp = new ArrayList<Integer>();
		List<Long> list = new ArrayList<Long>();
		for(int i=0;i<6;i++){
			List<Long> list2 = new ArrayList<Long>();
			list2.addAll(list);
			list = new ArrayList<Long>();
			if(i==0){
			   List<User> userList = userService.queryByExtensionUserId(userId);
			   if(userList.size()>0){ 
			      listTemp.add(userList.size());
			      for(User user:userList){
			    	  list.add(user.getId());
				  }
			   }else{
				   listTemp.add(0);
				   break;
			   }
			}else{
				int cou =0;
				for(Long temp:list2){
					List<User> userList = userService.queryByExtensionUserId(temp);
					cou=cou+userList.size();
					for(User user:userList){
				    	  list.add(user.getId());
					}
				}
				listTemp.add(cou);
			}	
		}
		if(listTemp.size()>1){
			map.put("m1", listTemp.get(0));
			map.put("mn", listTemp.get(1)+listTemp.get(2)+listTemp.get(3)+listTemp.get(4)+listTemp.get(5));
		}else{
			map.put("m1", listTemp.get(0));
			map.put("mn", listTemp.get(0));
		}
		User user = userService.queryByUserId(userId);
		map.put("inviteCode", user.getId().toString());	
		DigitalCurrencyInfo digitalCurrencyInfo=digitalCurrencyInfoService.queryByDigitalcurrencyname("MOL");
		//获取eth外账的数据  内账+外借+余额差值
		List<UserDigitalCurrencyInfo> userDigitalCurrencyList = userDigitalCurrencyInfoService.queryByDigitalCurrencyIdList(digitalCurrencyInfo.getId(), userId);
		try{
			map.put("bothPoint", userDigitalCurrencyList.get(0).getDigitalcurrencybalance().toPlainString());
		}catch(Exception e){
			System.out.println("getDistributionCount:"+userId);
		}
		map.put("createTime", new Date().getTime());
	    String userlink = "https://www.both.im/register.html?extensionUserId="+AESUtil.encryptAES(userId.toString(), PropertiesUtils.getInfoConfigProperties().getProperty("ase.pawd"));
		map.put("inviteUrl", userlink);
		String buffer ="【币圈】我在用币圈，100多万币友、3000多个币圈群等你来加入，你也来试试："+userlink+"? 注册就送100mol币，邀请好友还有更多奖励噢 🍭了解摩尔链信息请点击：https://www.mol.one";
		map.put("inviteDoc", buffer.toString());
		return ReturnInfo.info(200, map);
	}
	
	/**
	 * @info 得到用户分销详细数据
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getDistributionUserData", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getDistributionUserData(@RequestBody GetDistributionCountBean getDistributionCountBean){
		Long userId = getDistributionCountBean.getUserId();
		if(userId==null || getDistributionCountBean.getDataId()==null || getDistributionCountBean.getCount()==null){
			return ReturnInfo.error("参数错误！");
		}
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		
		List<DistributionRecord> distributionRecordList = distributionRecordService.queryByUserId(userId, getDistributionCountBean.getDataId(), getDistributionCountBean.getCount());
		if(distributionRecordList.size()==0){
			return ReturnInfo.info(200, listMap, false);
		}
		Date dateTime = DateUtils.formatDate(distributionRecordList.get(0).getCreatetime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		List<DistributionRecord> list = new ArrayList<DistributionRecord>();
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<distributionRecordList.size();i++){		  
		  if(calendar.getTime().getTime()<distributionRecordList.get(i).getCreatetime().getTime()){
			  String phone = distributionRecordList.get(i).getPhone();
			  List<UserSchedule> userSchedualList = userScheduleService.queryByUIdList(distributionRecordList.get(i).getFromuserid());
			  distributionRecordList.get(i).setHeadUrl(userSchedualList.get(0).getHeadicon());
			  phone=phone.substring(4, phone.length());
			  phone=phone.substring(0, 3)+"****"+phone.substring(7, phone.length());
			  distributionRecordList.get(i).setPhone(phone);
			  list.add(distributionRecordList.get(i));
		  }else{		  
			  map.put("shareTime", simpleDateFormat.format(calendar.getTime()));
			  map.put("shareList", list);
			  listMap.add(map);
			  calendar.add(Calendar.DATE, -1);
			  map = new HashMap<String, Object>();
			  list = new ArrayList<DistributionRecord>();
			  String phone = distributionRecordList.get(i).getPhone();
			  phone=phone.substring(4, phone.length());
			  phone=phone.substring(0, 3)+"****"+phone.substring(7, phone.length());
			  distributionRecordList.get(i).setPhone(phone);
			  List<UserSchedule> userSchedualList = userScheduleService.queryByUIdList(distributionRecordList.get(i).getFromuserid());
			  distributionRecordList.get(i).setHeadUrl(userSchedualList.get(0).getHeadicon());
			  list.add(distributionRecordList.get(i));
		  }	  
		}
		 map.put("shareTime", simpleDateFormat.format(calendar.getTime()));
		 map.put("shareList", list);
		 listMap.add(map);
		return ReturnInfo.info(200, listMap, true);
	}
	
	
	/**
	 * @info 判断用户好友是否注册
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getPhoneList", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getPhoneList(@RequestBody GetPhoneListBean getPhoneListBean){
		String phones=getPhoneListBean.getPhones();
		if(StringUtils.judgeBlank(phones)){
			return ReturnInfo.error("参数错误！");
		}
		List<String> phoneList = new ArrayList<String>();
		String[] phoneArray = phones.split(",");
		phoneArray=InternationalMobilePhoneNumberUtils.getPhoneInfo(phoneArray);
		List<User> phoneDistinguishList =userService.queryByPhoneDistinguish(phoneArray);
		if(phoneDistinguishList.size()==0){
			return ReturnInfo.info(200, phoneList);
		}
		for(User user:phoneDistinguishList){
			phoneList.add(user.getPhone());
		}
		return ReturnInfo.info(200, phoneList);
		
	}
	
	/**
	 * @info 更新头像
	 * @param
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("uploadHeadFile")
	public @ResponseBody ReturnInfo uploadHeadFile(MultipartFile file, String userName){	
		ReturnInfo r = new ReturnInfo();
		if(file==null || StringUtils.judgeBlank(userName)){
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
		//图片保存在文件夹下的userId目录下
		User user = userService.queryByUserNameList(userName).get(0);
		String path = PropertiesUtils.getInfoConfigProperties().getProperty("user.head.path");
		File filetemp = new File(path);
		if(!filetemp.exists()){
			filetemp.mkdirs();
		}
		String filePath = path+UUID.randomUUID().toString()+new Date().getTime()+fileSuffix;
		try {
			file.transferTo(new File(filePath));
			String fileTemp=filePath.substring(filePath.lastIndexOf("/"));
			fileTemp =PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr")+"/Avatar" + fileTemp;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("localfile", filePath);
			map.put("httpfile", fileTemp);
			r = ReturnInfo.info(200, map);
		} catch (Exception e) {
			logger.error("服务器保存文件失败！");
			r = ReturnInfo.error();
		}
		//数据入库
		userScheduleService.updateHeadPriture(filePath, user.getId());
		return r;
		
	} 
    
	/**
	 * @info 下载头像
	 * @param fileName
	 * @param response
	 * @param request
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="downloadHeadFile", method=RequestMethod.POST, consumes = "application/json")
    public ReturnInfo downloadHeadFile(HttpServletResponse response, HttpServletRequest request, @RequestBody DownloadHeadFileBean downloadHeadFileBean) throws Exception {
    	String userName=downloadHeadFileBean.getUserName();
		if(StringUtils.judgeBlank(userName)){
			return ReturnInfo.error("参数错误！");
		}
    	User user = userService.queryByUserNameList(userName).get(0);
        UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
    	File file=new File(userSchedule.getHeadicon());
    	String uploadFileName = file.getName();
    	String fileSuffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
	    // 设置响应头:内容处理方式 → attachment(附件,有为下载,没有为预览加载) →指定文件名
	    response.setHeader("Content-Disposition", "attachment;fileName=" + "head"+fileSuffix);
	    // 从服务器上读入程序中
	    InputStream fileInputStream = new FileInputStream(userSchedule.getHeadicon());
	    // 从程序中写出下载到客户端
	    OutputStream outputStream = response.getOutputStream();
	    // copy以及关闭流资源
	    IOUtils.copy(fileInputStream, outputStream);
	    outputStream.close();
	    fileInputStream.close();
	    return null;
    }
	
	/**
	 * @info 注册添加好友
	 * @param 手机号列表 与 userName
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="addFriend", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo addFriend(@RequestBody AddFriendBean addFriendBean){
		String userName=addFriendBean.getUserName();
		String phones=addFriendBean.getPhones();
		if(StringUtils.judgeBlank(userName) || StringUtils.judgeBlank(phones)){
			return ReturnInfo.error("参数错误！");
		}
		List<EasemobFriend> easemobFriendList = new ArrayList<EasemobFriend>();
		String[] phoneArray = phones.split(",");
		List<User> phoneDistinguishList =userService.queryByPhoneDistinguish(phoneArray);
		if(phoneDistinguishList.size()==0){
			return ReturnInfo.info(200, "该用户好友都没注册！");
		}
		for(int i=0;i<phoneDistinguishList.size();i++){
		  EasemobFriend easemobFriend = new EasemobFriend();
		  easemobFriend.setUserName(userName);
		  easemobFriend.setFriendName(phoneDistinguishList.get(i).getUsername());
		  easemobFriendList.add(easemobFriend);
		}
		EasemobUtils.EasemobAddFriend(easemobFriendList);
		return ReturnInfo.ok();	
	}
	
	/**
	 * @info 更改用户昵称
	 * @param 
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateNickname", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo updateNickname(@RequestBody UpdateNicknameBean updateNicknameBean){
		String userName = updateNicknameBean.getUserName();
		String nickName = updateNicknameBean.getNickName();
		String model = updateNicknameBean.getModel();
		if(StringUtils.judgeBlank(userName)||StringUtils.judgeBlank(nickName)){
			return ReturnInfo.error("参数错误！");
		}
	    if(!StringUtils.judgeBlank(model) && model.equals("Android")){
		   try {
				nickName = URLDecoder.decode(nickName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("安卓昵称解码失败！");
			}
	    }
		User user = userService.queryByUserNameList(userName).get(0);
		if(user==null){
			return ReturnInfo.error("参数错误！");
		}
		userScheduleService.updateNickname(user.getId(), nickName);
	   return ReturnInfo.ok();
    }
	
	/**
	 * @info 支付密码接口
	 * @param 
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updatepaypw", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo updatepaypw(@RequestBody UpdatepaypwBean updatepaypwBean){
		String userName = updatepaypwBean.getUserName();
		String paypw = updatepaypwBean.getPaypw();
		String oldpaypw = updatepaypwBean.getOldpaypw();
		if(StringUtils.judgeBlank(userName)||StringUtils.judgeBlank(paypw) || paypw.length()!=6){
			return ReturnInfo.error("参数错误！");
		}
		if(!StringUtils.judgeBlank(oldpaypw) && oldpaypw.length()!=6){
			return ReturnInfo.error("参数错误！");
		}
		ReturnInfo r = new ReturnInfo(); 
		if(StringUtils.judgeBlank(oldpaypw)){
		  User user = userService.queryByUserNameList(userName).get(0);
		  UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
		  if(userSchedule.getPaypwone()>0){
			  return ReturnInfo.error("参数错误！");
		  }
		  userScheduleService.updatepaypw(user.getId(), new Sha256Hash(paypw).toHex());
		  r=ReturnInfo.ok();
		}else{
			//判断旧密码是否正确
			User user = userService.queryByUserNameList(userName).get(0);
			UserSchedule sigupuserSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
			if(sigupuserSchedule.getPaypwone()==0){
				r=ReturnInfo.error("参数错误！");
			}
			List<UserSchedule> uslist = userScheduleService.queryByPaypw(sigupuserSchedule.getId(),new Sha256Hash(oldpaypw).toHex());
			if(uslist.size()==1){
				userScheduleService.updatepaypw(user.getId(), new Sha256Hash(paypw).toHex());
			}else{
				r=ReturnInfo.error("旧密码不正确！");
			}
		}
	   return r;
    }
	
	/**
	 * @info 得到引导界面群推荐数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getGuideGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getGuideGroupCategory(){
		List<GroupCategory> groupCategoryList = groupCategoryService.queryByData();
		if(groupCategoryList.size()==0){
			logger.info("数据库暂无群分类数据！");
			return ReturnInfo.error("暂无数据！");	
		}
		//得到列表下面的推荐系数最高的3个群数据
		for(GroupCategory groupCategory:groupCategoryList){
			//查询数据
			List<com.yishenxiao.usermanager.beans.Group> groupList = groupService.queryByGCId(groupCategory.getId(), 3);
			groupCategory.setGroupList(groupList);
		}
		return ReturnInfo.info(200,groupCategoryList);	
	}
	
	/**
	 * @info 得到好友 ,搭建图片服务器后要改
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserFriends", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getUserFriends(@RequestBody GetUserFriendsBean getUserFriendsBean){
		String userName=getUserFriendsBean.getUserName();
		if(StringUtils.judgeBlank(userName)){
			return ReturnInfo.error("参数错误！");
		}
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		Object result = EasemobUtils.EasemobFriendData(userName);
		if(!result.toString().contains("error_code")){
			EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(result.toString(), EasemobFriendBean.class);
		    for(int i=0;i<easemobFriendBean.getData().size();i++){
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	String easeName = easemobFriendBean.getData().get(i);
		    	User user = userService.queryByUserNameList(easeName).get(0);
		    	UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
		    	map.put("easemobid", user.getUsername());
		    	map.put("account", user.getAccount());
		    	map.put("nickName", userSchedule.getNickname());
		    	map.put("headIcon", userSchedule.getHeadicon());
		    	rlist.add(map);
		    }
		  
		}else{
			logger.error(result.toString());
			return ReturnInfo.error("请重试！");
		}
		return ReturnInfo.info(200, rlist);
	}
	
	/**
	 * @info 根据数据得到好友数据
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getFriendData", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo getFriendData(@RequestBody UserFriendsBean userFriendsBean){
		if(StringUtils.judgeBlank(userFriendsBean.getUserName())){
			return ReturnInfo.error("参数错误！");
		}
		String userName = userFriendsBean.getUserName();
		String phones = userFriendsBean.getPhones();
		Set<String> setTemp = new HashSet<String>();
		if(!StringUtils.judgeBlank(phones)){
			String[] phoneArray = phones.split(",");
			phoneArray=InternationalMobilePhoneNumberUtils.getPhoneInfo(phoneArray);
			//用户数据
			List<String> phoneList = new ArrayList<String>();
			for(int t=0;t<phoneArray.length;t++){
				phoneList.add(phoneArray[t]);
				if(t>1 && t%10==0){
					List<User> phoneDistinguishList =userService.queryByPhoneDistinguishList(phoneList);
					for(User user:phoneDistinguishList){
						setTemp.add(user.getUsername());
					}
					phoneList.removeAll(phoneList);
				}
			}
			if(phoneList.size()!=0){
				List<User> phoneDistinguishList =userService.queryByPhoneDistinguishList(phoneList);
				for(User user:phoneDistinguishList){
					setTemp.add(user.getUsername());
				}
				phoneList.removeAll(phoneList);
			}
		}
		//用户
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		Object result = EasemobUtils.EasemobFriendData(userName);
		if(!result.toString().contains("error_code")){
			EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(result.toString(), EasemobFriendBean.class);
			for(int i=0;i<easemobFriendBean.getData().size();i++){
		    	setTemp.add(easemobFriendBean.getData().get(i));
		    }
		}else{
			logger.error(result.toString());
			return ReturnInfo.error("请重试！");
		}
		
		Iterator<String> iterator = setTemp.iterator();
	    while(iterator.hasNext()){
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	List<User> userList=userService.queryByUserNameList(iterator.next());
	    	if(userList.size()==0){
	    		continue;
	    	}
	    	User user = userList.get(0);
	    	UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
	    	map.put("easemobid", user.getUsername());
	    	map.put("account", user.getAccount());
	    	map.put("nickName", userSchedule.getNickname());
	    	map.put("headIcon", userSchedule.getHeadicon());
	    	rlist.add(map);
	    }
		return ReturnInfo.info(200, rlist);
		
	}
	
	
	/**
	 * @info 根据环信id得到用户数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroups", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroups(@RequestBody GetUserGroupsBean getUserGroupsBean){
		String userName=getUserGroupsBean.getUserName();
		if(StringUtils.judgeBlank(userName)){
			return ReturnInfo.error("参数错误！");	
		}
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		Object result = EasemobUtils.EasemobFriendData(userName);
		if(!result.toString().contains("error_code")){
			EasemobFriendBean easemobFriendBean = JsonUtils.jsonToObj(result.toString(), EasemobFriendBean.class);
		    for(int i=0;i<easemobFriendBean.getData().size();i++){
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	String easeName = easemobFriendBean.getData().get(i);
		    	User user = userService.queryByUserNameList(easeName).get(0);
		    	UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
		    	map.put("nickName", userSchedule.getNickname());
		    	map.put("headIcon", userSchedule.getHeadicon());
		    	rlist.add(map);
		    }
		  
		}else{
			logger.error(result.toString());
			return ReturnInfo.error("请重试！");
		}
		return ReturnInfo.info(200, rlist);
	}
	
	/*
	 * 支付发送短信
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userPhoneCode", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo userPhoneCode(HttpServletRequest req, @RequestBody UserPhoneCode userPhoneCode){
		String account=userPhoneCode.getAccount();
		String nativecode=userPhoneCode.getNativecode();
		Integer type=userPhoneCode.getType();
		   ReturnInfo rInfo = new ReturnInfo();
		   //检查用户
		   if(StringUtils.judgeBlank(account)){
			   return ReturnInfo.error("参数错误！");
		   }	   
		   if (userPhoneCode.getNativecode().equals("0086") && !account.matches("^1[34578]\\d{9}$")) {
			   return ReturnInfo.error("参数错误！");
		   }
		   if(StringUtils.judgeBlank(nativecode)){
			   return ReturnInfo.error("参数错误！");
		   }
		   String temp="";
		   if(type==null){
			   return ReturnInfo.error("参数错误！");
		   }else{
			   if(type==0){
				   temp="setThePassword";
			   }else{
				   temp="forgetThePassword";
			   }
		   }
		   String phonecode = captchaProducer.createText();	   
		   //发送短信
		   try{
			   //10分钟短信认证频率设置
			   Object sessionCode = redisService.opsForValue().get(nativecode+account+temp);
			   if(sessionCode!=null){
				   return ReturnInfo.error("10分钟以内验证码有效！");
			   }
			   //ip限制
			   String ip=VerificationCodeController.getIpAddr(req);
			   List<IpAddr> ipAddrList = ipAddrService.queryByIp(ip);
			   if(ipAddrList.size()!=0){
				   List<ProtertiesData> protertiesDataList = protertiesDataService.queryByDataKeyList("ipCount");
				   if(ipAddrList.get(0).getCount()>=Integer.parseInt(protertiesDataList.get(0).getDatavalue())){
					   return ReturnInfo.error("该ip地址今日短信数量已达限制！");
				   }
				   //ip 地址计数加1,并记录ip下的手机号码
				   if(!ipAddrList.get(0).getPhone().contains(account)){
					   ipAddrList.get(0).setPhone(ipAddrList.get(0).getPhone()+","+nativecode+account);
				   }
				   ipAddrService.updateByCount(ipAddrList.get(0));	
			   }else{
				   IpAddr ipAddr = new IpAddr();
				   ipAddr.setCount(1);
				   ipAddr.setIpaddr(ip);
				   ipAddr.setPhone(nativecode+account);
				   ipAddrService.insertData(ipAddr);
			   }		   
			   int result = new LoginController().pushDomesticInfo(req, account ,phonecode, userPhoneCode.getNativecode());
			   if(result==0){
				   redisService.opsForValue().set(nativecode+account+temp, phonecode, 10, TimeUnit.MINUTES);
				   rInfo =ReturnInfo.ok();
			   }else{
				   rInfo = ReturnInfo.error("短信发送失败！");
			   }
		   }catch(Exception e){
			   logger.error(nativecode+account+"支付验证码短信发送失败！");
			   rInfo = ReturnInfo.error("fail");
		   }  
		return rInfo; 
	   }
	
	/*
	 * 支付密码验证
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="paymentPasswordcheck", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo paymentPasswordcheck(@RequestBody PaymentPasswordCheckBean paymentPasswordCheckBean){
		String payPassword = paymentPasswordCheckBean.getPayPassword();
		Long userId = paymentPasswordCheckBean.getUserId();
		// 检查用户
		if (StringUtils.judgeBlank(payPassword) || userId == null || payPassword.length() != 6) {
			return ReturnInfo.error("参数错误！");
		}
		UserSchedule userSchedule = userScheduleService.queryByUIdList(userId).get(0);
		List<UserSchedule> uslist = userScheduleService.queryByPaypw(userSchedule.getId(),
				new Sha256Hash(payPassword).toHex());
		if (uslist.size() == 1) {
			List<UserTransferAccountsDetails> userTransferAccountsDetailsList = userTransferAccountsDetailsService
					.queryByUserId(userId);
			return ReturnInfo.info(200, userTransferAccountsDetailsList.size());
		}
		return ReturnInfo.error("支付密码不正确！");
    }
	
	/*
	 * 支付验证码验证
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="userPhoneCodeVerification", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo userPhoneCodeVerification(@RequestBody UserPhoneCodeVerificationBean userPhoneCodeVerificationBean){
		   String account=userPhoneCodeVerificationBean.getAccount();
		   String nativecode=userPhoneCodeVerificationBean.getNativecode();
		   String phonecode=userPhoneCodeVerificationBean.getPhonecode();
		   Integer type=userPhoneCodeVerificationBean.getType();
		   //检查用户
		   if(StringUtils.judgeBlank(account)){
			   return ReturnInfo.error("参数错误！");
		   }
		   
		   if (userPhoneCodeVerificationBean.getNativecode().equals("0086") && !account.matches("^1[34578]\\d{9}$")) {
				return ReturnInfo.error("参数错误！");
			}
		   if(StringUtils.judgeBlank(nativecode)){
			   return ReturnInfo.error("参数错误！");
		   }
		   if(StringUtils.judgeBlank(phonecode)){
			   return ReturnInfo.error("参数错误！");
		   }
		   String temp="";
		   if(type==null){
			   return ReturnInfo.error("参数错误！");
		   }else{
			   if(type==0){
				   temp="setThePassword";
			   }else{
				   temp="forgetThePassword";
			   }
		   }
		   //检查验证码
		   Object sessionCode = redisService.opsForValue().get(nativecode+account+temp);
		   if(sessionCode==null){
			   return ReturnInfo.error("支付验证码错误或者已经过期！");
		   }
		   if(!phonecode.equals(sessionCode.toString())){
		 			return ReturnInfo.error("手机验证码错误或者已经过期！");
		   }else{
			   redisService.opsForValue().set(nativecode+account+temp, "", 1, TimeUnit.SECONDS);
		   }
	   return ReturnInfo.ok("验证成功！"); 
    }

	/*
	 * 忘记密码设置
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="forgetToPayThePassword", method=RequestMethod.POST,consumes = "application/json")
	 public @ResponseBody ReturnInfo forgetToPayThePassword(@RequestBody ForgetToPayThePasswordBean forgetToPayThePasswordBean){
		String userName =forgetToPayThePasswordBean.getUserName();
		String paypw=forgetToPayThePasswordBean.getPaypw();
		if(StringUtils.judgeBlank(userName)||StringUtils.judgeBlank(paypw) || paypw.length()!=6){
			return ReturnInfo.error("参数错误！");
		}
		ReturnInfo r = new ReturnInfo(); 
		User user = userService.queryByUserNameList(userName).get(0);
		userScheduleService.updatepaypw(user.getId(), new Sha256Hash(paypw).toHex());
		r=ReturnInfo.ok();
	   return r;
    }
	
	/*
	 * 更新用户数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateUserInfo", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo updateUserInfo(@RequestBody UpdateUserInfoBean updateUserInfoBean){
	       String userName = updateUserInfoBean.getUserName();
	       String nickName = updateUserInfoBean.getNickName();
	       String model = updateUserInfoBean.getModel();
	       String headIcon = updateUserInfoBean.getHeadIcon();
		   //检查用户
		   if(StringUtils.judgeBlank(userName)){
			   return ReturnInfo.error("参数错误！");
		   }
		   if(StringUtils.judgeBlank(nickName) && StringUtils.judgeBlank(headIcon)){
			   return ReturnInfo.error("参数错误！");
		   }
		   if(!StringUtils.judgeBlank(model) && model.equals("Android")){
			   try {
				nickName = URLDecoder.decode(nickName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("安卓昵称解码失败！");
			}
		   }
		   User user = userService.queryByUserNameList(userName).get(0);
		   
		   UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
		   UserSchedule userScheduletemp = new UserSchedule();
		   userScheduletemp.setId(userSchedule.getId());	
		   userScheduletemp.setNickname(nickName);
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
						  logger.error(userName+"生成数据失败，请联系客服！");
						  return ReturnInfo.error("生成数据失败，请联系客服！");
					   }else if(suc==2){
						   return ReturnInfo.error("请耐心等待数据生成！");
					   }
			       }
		       }   
			   Random rad = new Random();
			   int temp =rad.nextInt(15);
			   if(temp==0){
				 temp =15;
			   }
			   if(StringUtils.judgeBlank(headIcon)){
			     headIcon="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/"+temp+".png";			   
			   }
			   userScheduletemp.setHeadicon(headIcon);
		   }else{
			   userScheduletemp.setHeadicon(headIcon);
		   }
		   int c=userScheduleService.updateUserSchedule(userScheduletemp);
		   if(c==1){
			   Nickname nickname = new Nickname();
			   nickname.setNickname(nickName);
			   Object result=easemobIMUsers.modifyIMUserNickNameWithAdminToken(userName, nickname);
			   if(result.toString().contains("error_code")){
		     	   EasemobErrorInfo easemobErrorInfo = JsonUtils.jsonToObj(result.toString(), EasemobErrorInfo.class);
		     	   if(easemobErrorInfo.getError_code()==400){
		     		  return ReturnInfo.error("您的账号异常，请联系客服！");
			       }
		     	   logger.error(user.getUsername()+"  修改环信用户昵称失败， "+easemobErrorInfo.getError_code()+"  请联系客服！");
		        }
		   }else{
			   return ReturnInfo.ok("修改失败！"); 
		   }
	   return ReturnInfo.ok("修改成功！"); 
    }
	
	/*
	 * 得到用户群组数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserGroupData", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo getUserGroupData(@RequestBody GetUserGroupDataBean  getUserGroupDataBean){
		   Long userId = getUserGroupDataBean.getUserId();
		   //检查用户
		   if(userId==null){
			   return ReturnInfo.error("参数错误！");
		   }
		   List<UserRelationGroup> userRelationGroupList = userRelationGroupService.qureyByUserId(userId);
		   if(userRelationGroupList.size()==0){
			   return ReturnInfo.error("该用户暂时没有加入任何群！");
		   }
		   List<Long> groupIds = new ArrayList<Long>();
		   for(int i=0;i<userRelationGroupList.size();i++){
			   groupIds.add(userRelationGroupList.get(i).getGroupid());
		   }
		   List<Group> rgroup = groupService.qureyUserData(groupIds);
	   return ReturnInfo.info(200, rgroup); 
    }
   
	
	/*
	 * 根据用户环信id得到用户数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getUserData", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo getUserData(@RequestBody GetUserData getUserData){
		   String userName=getUserData.getUserName();
		   //检查用户
		   if(StringUtils.judgeBlank(userName)){
			   return ReturnInfo.error("参数错误！");
		   }
		   List<User> listUser = userService.queryByUserNameList(userName);
		   if(listUser.size()==0){
			   return ReturnInfo.error("参数错误！");
		   }
		   User user = listUser.get(0);
		   UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
		   SignUpBean signUpBean = new SignUpBean();
		   signUpBean.setUser(user);
		   userSchedule.setHeadicon(userSchedule.getHeadicon());
		   signUpBean.setUserSchedule(userSchedule);
	   return ReturnInfo.info(200, signUpBean); 
    }
	
	/**
	 * @Info 根据环信id批量获取用户id
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getBatchUserData", method=RequestMethod.POST,consumes = "application/json")
	public @ResponseBody ReturnInfo getBatchUserData(@RequestBody GetBatchUserDataBean getBatchUserDataBean){
		   String easemobIds=getBatchUserDataBean.getEasemobIds();
		   //检查用户
		   if(StringUtils.judgeBlank(easemobIds)){
			   return ReturnInfo.error("参数错误！");
		   }
		   List<SignUpBean> list = new ArrayList<SignUpBean>();
		   String[] eIds = easemobIds.split(",");
		   for(int i=0;i<eIds.length;i++){
			   User user = userService.queryByUserNameList(eIds[i]).get(0);
			   if(user==null){
				   continue;
			   }
			   UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);
			   SignUpBean signUpBean = new SignUpBean();
			   signUpBean.setUser(user);
			   userSchedule.setHeadicon(userSchedule.getHeadicon());
			   signUpBean.setUserSchedule(userSchedule);
			   list.add(signUpBean);
		   }
	   return ReturnInfo.info(200, list); 
	}
}
