package com.yishenxiao.digitalwallet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.digitalwallet.beans.CoinsList;
import com.yishenxiao.digitalwallet.beans.DetailsOfRedEnvelopesBean;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.ReaEnvelopesDetailsBean;
import com.yishenxiao.digitalwallet.beans.RedEnvelopes;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;
import com.yishenxiao.digitalwallet.beans.postbean.BalanceInquiryBean;
import com.yishenxiao.digitalwallet.beans.postbean.DetailsOfRedEnvelopesPostBean;
import com.yishenxiao.digitalwallet.beans.postbean.UsersRedPacketsDetailsBean;
import com.yishenxiao.digitalwallet.beans.postbean.UsersRobbingRedPacketsBean;
import com.yishenxiao.digitalwallet.beans.postbean.UsersSendRedPacketsBean;
import com.yishenxiao.digitalwallet.service.CoinsListService;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.MoneyDataService;
import com.yishenxiao.digitalwallet.service.RedEnvelopesService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserRedEnvelopesService;
import com.yishenxiao.digitalwallet.service.WalletThingService;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("userDigitalCurrency")
public class UserDigitalCurrencyInfoController {

	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("walletThingService")
	private WalletThingService walletThingService;
	
	@Autowired(required=false)@Qualifier("redEnvelopesService")
	private RedEnvelopesService redEnvelopesService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisService;
	
	@Autowired(required=false)@Qualifier("userRedEnvelopesService")
    private UserRedEnvelopesService userRedEnvelopesService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userDigitalAddrService")
    private UserDigitalAddrService userDigitalAddrService;
	
	@Autowired(required=false)@Qualifier("moneyDataService")
    private MoneyDataService moneyDataService;
	
	@Autowired(required=false)@Qualifier("coinsListService")
    private CoinsListService coinsListService;
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "purseBalance", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo purseBalance(@RequestBody BalanceInquiryBean balanceInquiryBean) {
		String userId = balanceInquiryBean.getUserId();
		if (StringUtils.judgeBlank(userId)) {
			return ReturnInfo.error("参数错误！");
		}
		return walletThingService.balanceInquiry(Long.parseLong(userId));
	}
	
	/**
	 * @info 得到用户币数据  根据各个用户三大体系的地址生成二维码
	 * @return
	 */
    /*public @ResponseBody ReturnInfo getUserDigitalCurrencyInfo(Long userId){
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		List<UserDigitalCurrencyInfoBean> userDigitalCurrencyInfoBeanList = new ArrayList<UserDigitalCurrencyInfoBean>();
		List<UserDigitalCurrencyInfo> userDigitalCurrencyList = userDigitalCurrencyInfoService.queryUserDigitalCurrencyInfoData(userId);
		//是否生成二维码
		String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.qrcode.path");
		File file = new File(headicon);
		if(!file.exists()){
			file.mkdirs();
		}
		for(int i=0;i<userDigitalCurrencyList.size();i++){
			UserDigitalCurrencyInfoBean userDigitalCurrencyInfoBean = new UserDigitalCurrencyInfoBean();
			userDigitalCurrencyInfoBean.setId(userDigitalCurrencyList.get(i).getId());
			userDigitalCurrencyInfoBean.setUserid(userDigitalCurrencyList.get(i).getUserid());
			//查询
			DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryById(userDigitalCurrencyList.get(i).getDigitalcurrencyid());
			if(!StringUtils.judgeBlank(digitalCurrencyInfo.getDigitalcurrencyaddr())){
				//代币
				userDigitalCurrencyInfoBean.setDigitalcurrency(digitalCurrencyInfo.getDigitalcurrencyname());
				userDigitalCurrencyInfoBean.setFee(digitalCurrencyInfo.getFee().doubleValue());
				UserDigitalAddr userDigitalAddr = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo.getDigitalcurrencyinfoid());
				userDigitalCurrencyInfoBean.setMoneyAddr(userDigitalAddr.getCurrencyaddr());
				DigitalCurrencyInfo digitalCurrencyInfoData = digitalCurrencyInfoService.queryById(digitalCurrencyInfo.getDigitalcurrencyinfoid());
				String qrStr=headicon+userId+digitalCurrencyInfoData.getId()+".png";
			    if(!new File(qrStr).exists()){
			    	try {
						QRCodeUtils.generateQRCode(userDigitalAddr.getCurrencyaddr(), 300, 300, "png", userId.toString()+digitalCurrencyInfoData.getId().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			    String fileTemp=qrStr.substring(qrStr.lastIndexOf("/"));
				fileTemp =PropertiesUtils.getInfoConfigProperties().getProperty("info.qrcodeAddr")+ "/qrcode" + fileTemp;
				userDigitalCurrencyInfoBean.setFilePath(fileTemp);
				//获取eth外账的数据  内账+外借+余额差值
				Double digitalcurrencybalance = userDigitalCurrencyList.get(i).getBorrowedbalance().doubleValue()+userDigitalCurrencyList.get(i).getDigitalcurrencybalance().doubleValue()
						              +userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue();
				Map<String, Object> paraterMap = new HashMap<String, Object>();
				paraterMap.put("token_address", digitalCurrencyInfo.getDigitalcurrencyaddr());//钱包地址
				paraterMap.put("pub_address", userDigitalAddr.getCurrencyaddr());//用户地址
				paraterMap.put("token_identifier", digitalCurrencyInfo.getDigitalcurrencyname());//币的名字
				String result = HttpClientUtils.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"ethToken/getEthTokenBalance", paraterMap);
				Double balance = Double.parseDouble(result)/Math.pow(10, digitalCurrencyInfo.getTokendecimal());
				//更新数据库的外账数据
				userDigitalCurrencyInfoService.updateForeignUserData(userId, digitalCurrencyInfo.getId(), balance);
				if(userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue()<balance){
					digitalcurrencybalance=digitalcurrencybalance+(balance-userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue());
				}else{
					digitalcurrencybalance=digitalcurrencybalance-(userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue()-balance);
				}
				userDigitalCurrencyInfoBean.setDigitalcurrencybalance(new BigDecimal(digitalcurrencybalance.toString()).setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
				userDigitalCurrencyInfoBeanList.add(userDigitalCurrencyInfoBean);
			}else{
				//ETH
				userDigitalCurrencyInfoBean.setDigitalcurrency(digitalCurrencyInfo.getDigitalcurrencyname());
				userDigitalCurrencyInfoBean.setFee(digitalCurrencyInfo.getFee().doubleValue());
				UserDigitalAddr userDigitalAddr = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo.getId());
				userDigitalCurrencyInfoBean.setMoneyAddr(userDigitalAddr.getCurrencyaddr());
				//获取eth外账的数据  内账+外借+余额差值
				Double digitalcurrencybalance = userDigitalCurrencyList.get(i).getBorrowedbalance().doubleValue()+userDigitalCurrencyList.get(i).getDigitalcurrencybalance().doubleValue()
						              +userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue();
				Map<String, Object> paraterMap = new HashMap<String, Object>();
				paraterMap.put("address", userDigitalAddr.getCurrencyaddr());
				String result = HttpClientUtils.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"eth/getEthBalance", paraterMap);
				Double balance= Double.parseDouble(result);
				//更新数据库的外账数据
				userDigitalCurrencyInfoService.updateForeignUserData(userId, digitalCurrencyInfo.getId(), balance);
				if(userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue()<balance){
					digitalcurrencybalance=digitalcurrencybalance+(balance-userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue());
				}else{
					digitalcurrencybalance=digitalcurrencybalance-(userDigitalCurrencyList.get(i).getForeignaccountbalance().doubleValue()-balance);
				}
				userDigitalCurrencyInfoBean.setDigitalcurrencybalance(new BigDecimal(digitalcurrencybalance.toString()).setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
				DigitalCurrencyInfo digitalCurrencyInfoData = digitalCurrencyInfo;
				String qrStr=null;
				qrStr=headicon+userId+digitalCurrencyInfoData.getId()+".png";
				if(!new File(qrStr).exists()){
			    	try {
						QRCodeUtils.generateQRCode(userDigitalAddr.getCurrencyaddr(), 300, 300, "png", userId.toString()+digitalCurrencyInfoData.getId().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }	    
			    String fileTemp=qrStr.substring(qrStr.lastIndexOf("/"));
				fileTemp =PropertiesUtils.getInfoConfigProperties().getProperty("info.qrcodeAddr")+ "/qrcode" + fileTemp;
				userDigitalCurrencyInfoBean.setFilePath(fileTemp);
				userDigitalCurrencyInfoBeanList.add(userDigitalCurrencyInfoBean);
			}
			
		}
		return ReturnInfo.info(200, userDigitalCurrencyInfoBeanList);
	}*/
	
	/**
	 * @info 钱包余额接口
	 * @return
	 */ 
	
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="purseBalance", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo purseBalance(@RequestBody PurseBalancePostBean purseBalancePostBean){
		Long userId=purseBalancePostBean.getUserId();
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		List<PurseBalanceBean> purseBalanceBeanList = new ArrayList<PurseBalanceBean>();
		ReturnInfo returnInfo= getUserDigitalCurrencyInfo(userId);
		List<UserDigitalCurrencyInfoBean> userDigitalCurrencyInfoBeanList =(List<UserDigitalCurrencyInfoBean>) returnInfo.get("data");
		List<MoneyData> moneyDataList = moneyDataService.queryByData();
		String selfIp=PropertiesUtils.getInfoConfigProperties().getProperty("info.ip");
		String coinIconPort=PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort");
		for(int i=0;i<moneyDataList.size();i++){
			PurseBalanceBean purseBalanceBean = new PurseBalanceBean();
			purseBalanceBean.setCoinName(moneyDataList.get(i).getCoinname());
			for(int z=0;z<userDigitalCurrencyInfoBeanList.size();z++){
				UserDigitalCurrencyInfoBean userDigitalCurrencyInfoBean = userDigitalCurrencyInfoBeanList.get(z);
				if(userDigitalCurrencyInfoBean.getDigitalcurrency().equals(moneyDataList.get(i).getName().toUpperCase())){
				   purseBalanceBean.setFee(new BigDecimal(userDigitalCurrencyInfoBean.getFee()).setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
				   purseBalanceBean.setBalance(new BigDecimal(userDigitalCurrencyInfoBean.getDigitalcurrencybalance()).setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
				   purseBalanceBean.setFilePath(userDigitalCurrencyInfoBean.getFilePath());
				   purseBalanceBean.setId(moneyDataList.get(i).getId());
				   if(moneyDataList.get(i).getName().equals("ETH")){
				       purseBalanceBean.setImageUrl("http://"+selfIp+":"+coinIconPort+"/currencyLogo/eth.png");
				   }else if(moneyDataList.get(i).getName().equals("MVC")){
					   purseBalanceBean.setImageUrl("http://"+selfIp+":"+coinIconPort+"/currencyLogo/mvc.png");
				   }else if(moneyDataList.get(i).getName().equals("MOL")){
					   purseBalanceBean.setImageUrl("http://"+selfIp+":"+coinIconPort+"/currencyLogo/mol.png");
				   }else if(moneyDataList.get(i).getName().equals("MDT")){
					   purseBalanceBean.setImageUrl("http://"+selfIp+":"+coinIconPort+"/currencyLogo/mdt.png");
				   }
				   purseBalanceBean.setMarketPrice(moneyDataList.get(i).getMarketprice().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
				   purseBalanceBean.setMoneyAddr(userDigitalCurrencyInfoBean.getMoneyAddr());
				   purseBalanceBean.setName(moneyDataList.get(i).getName());
				}			   
			}
			purseBalanceBeanList.add(purseBalanceBean);
		}
		return ReturnInfo.info(200, purseBalanceBeanList);
	}*/
	
	
	/**
	 * @info 外账付款同步内账ETH数据
	 * @return
	 */ 
	
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="accountOperation", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo accountOperation(@RequestBody AccountOperationBean accountOperationBean){
		String purseAddress=accountOperationBean.getPurseAddress();
		Double foreignAccount=accountOperationBean.getForeignAccount();
		Double borrowFrom=accountOperationBean.getBorrowFrom();
		if(StringUtils.judgeBlank(purseAddress) && foreignAccount==null && borrowFrom==null){
			return ReturnInfo.error("参数错误！");
		}
		List<UserDigitalAddr> userDigitalAddr = userDigitalAddrService.queryAddr(purseAddress);
		if(userDigitalAddr.size()==0){
			return ReturnInfo.error("参数错误！");
		}else{
			walletThingService.accountOperation(userDigitalAddr, foreignAccount, borrowFrom);
		}
		return ReturnInfo.ok();
	}*/
	
	
	/**
	 * @info 用户发红包
	 * @return
	 */
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="usersSendRedPackets", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo usersSendRedPackets(@RequestBody UsersSendRedPacketsBean usersSendRedPacketsBean){
		//return ReturnInfo.error("内测版本，暂不支持红包！");
	    Long userId=usersSendRedPacketsBean.getUserId();
		String REDesc=usersSendRedPacketsBean.getREDesc();
		String currency=usersSendRedPacketsBean.getCurrency();
		Double amount=usersSendRedPacketsBean.getAmount();
		Integer count=usersSendRedPacketsBean.getCount();
		String payPass=usersSendRedPacketsBean.getPayPass();
		if(userId==null || StringUtils.judgeBlank(currency) || amount==null || StringUtils.judgeBlank(payPass)){
			return ReturnInfo.error("参数错误！");
		}
		//异常用户不能发红包
		List<CoinsList> coinsList = coinsListService.queryByUserId(userId);
		if(coinsList.size()>0){
			return ReturnInfo.error("该账户存在违规操作，请联系客服！");
		}
		if(amount<=0){
			return ReturnInfo.error("红包金额不能为负数！");
		}
		UserSchedule userSchedule = userScheduleService.queryByUIdList(userId).get(0);
		List<UserSchedule> uslist = userScheduleService.queryByPaypw(userSchedule.getId(),new Sha256Hash(payPass).toHex());
		if(uslist.size()!=1){
			return ReturnInfo.error("转账失败！"); 
		}
		
		if(count==null){
			count=1;
		}
		
		if(count>100){
			return ReturnInfo.error("红包个数暂时不能超过100个");
		}
		
		if(StringUtils.judgeBlank(REDesc)){
			REDesc="一币一山河，谁还要嫩模!";
		}
		if(count>1 && amount<0.0001){
			return ReturnInfo.error("群红包发出的金额必须大于0.0001");
		}
		//查询币的数量
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currency);
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userId);
		if(userDigitalCurrencyInfo.getDigitalcurrencybalance().doubleValue()<amount){
			return ReturnInfo.error("余额不足！");
		}	
		Map<String,Object> map = walletThingService.usersSendRedPackets(userId, currency, amount, count, REDesc, userDigitalCurrencyInfo.getDigitalcurrencyid());
		return ReturnInfo.info(200, map);
		
	}
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="usersRobbingRedPackets", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo usersRobbingRedPackets(@RequestBody UsersRobbingRedPacketsBean usersRobbingRedPacketsBean){
		return ReturnInfo.error("低版本，不支持抢红包，请前往官网更新！");
	}
	
	/**
	 * @info 判断红包是否领完
	 * @return
	 */
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="judgUserRedPackets", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo judgUserRedPackets(@RequestBody UsersRobbingRedPacketsBean usersRobbingRedPacketsBean){
		Long userId=usersRobbingRedPacketsBean.getUserId();
		Long redEnvelopesId=usersRobbingRedPacketsBean.getRedEnvelopesId(); 
		String dataKey=usersRobbingRedPacketsBean.getDataKey();
		if(userId==null || redEnvelopesId==null || StringUtils.judgeBlank(dataKey)){
				return ReturnInfo.error("参数错误！");
		}
		//判断用户是否抢过红包
		List<UserRedEnvelopes> userRedEnvelopesList = userRedEnvelopesService.queryByUIDREDID(userId, redEnvelopesId);
		if(userRedEnvelopesList.size()>0){
			return ReturnInfo.error("您已经领取过红包!");
		}
		if(redisService.opsForValue().get(dataKey)==null){
			return ReturnInfo.error("红包已经过期!");
		}
		//查询红包金额
		RedEnvelopes redEnvelopes = (RedEnvelopes)redisService.opsForValue().get(dataKey);
		if(redEnvelopes.getCount()>0){
			return ReturnInfo.info(200,true);
		}else{
			return ReturnInfo.error("红包已经抢完!");
		}	
	}
	
	/**
	 * @info 用户抢红包
	 * @return
	 */
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="usersRobbingRedPacketsNew", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo usersRobbingRedPacketsNew(@RequestBody UsersRobbingRedPacketsBean usersRobbingRedPacketsBean){
		Long userId=usersRobbingRedPacketsBean.getUserId();
		Long redEnvelopesId=usersRobbingRedPacketsBean.getRedEnvelopesId(); 
		String dataKey=usersRobbingRedPacketsBean.getDataKey();
		if(userId==null || redEnvelopesId==null || StringUtils.judgeBlank(dataKey)){
				return ReturnInfo.error("参数错误！");
		}
		//异常用户不能抢红包
		List<CoinsList> coinsList = coinsListService.queryByUserId(userId);
		if(coinsList.size()>0){
			return ReturnInfo.error("该账户存在违规操作，请联系客服！");
		}
		//判断用户是否抢过红包
		List<UserRedEnvelopes> userRedEnvelopesList = userRedEnvelopesService.queryByUIDREDID(userId, redEnvelopesId);
		if(userRedEnvelopesList.size()>0){
			return ReturnInfo.error("您已经领取过红包!");
		}
		if(redisService.opsForValue().get(dataKey)==null){
			return ReturnInfo.error("红包已经过期!");
		}
		UserRedEnvelopes userRedEnvelopes = walletThingService.usersRobbingRedPackets(userId,redEnvelopesId,dataKey);
		if(userRedEnvelopes==null){
			return ReturnInfo.info(201, "红包已经抢完!");
		}else{
		   userRedEnvelopesService.insertUserRedEnvelopesData(userRedEnvelopes);
		}
		return ReturnInfo.info(200,userRedEnvelopes);
	}
	
	/**
	 * @info 用户红包详情
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="usersRedPacketsDetails", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo usersRedPacketsDetails(@RequestBody UsersRedPacketsDetailsBean usersRedPacketsDetailsBean){
		Long redEnvelopesId=usersRedPacketsDetailsBean.getRedEnvelopesId();
		String dataKey=usersRedPacketsDetailsBean.getDataKey();
		if(redEnvelopesId==null && StringUtils.judgeBlank(dataKey)){
				return ReturnInfo.error("参数错误！");
		}
		List<ReaEnvelopesDetailsBean> ReaEnvelopesDetailsBeanList = new ArrayList<ReaEnvelopesDetailsBean>();
		Map<String, Object> map = new HashMap<String,Object>();
		RedEnvelopes redEnvelopes = redEnvelopesService.queryRedEnvelopesData(redEnvelopesId);
		User user = userService.queryByUserId(redEnvelopes.getUserid());
		if(user==null){
			return ReturnInfo.info(200,"不在同一个服务器！");
		}
		RedEnvelopes redEnvelopesRedis = redEnvelopesService.queryByRedisKey(dataKey).get(0);
		List<UserRedEnvelopes> userRedEnvelopesList = userRedEnvelopesService.queryByRedEnvelopesId(redEnvelopesId);
		for(int i=0;i<userRedEnvelopesList.size();i++){
			ReaEnvelopesDetailsBean reaEnvelopesDetailsBean = new ReaEnvelopesDetailsBean();
			reaEnvelopesDetailsBean.setCreateTime(userRedEnvelopesList.get(i).getCreatetime());
			reaEnvelopesDetailsBean.setMonery(userRedEnvelopesList.get(i).getMonery().doubleValue());
			User temp = userService.queryByUserId(userRedEnvelopesList.get(i).getUserid());
			UserSchedule userSchedule = userScheduleService.queryByUIdList(temp.getId()).get(0);
			reaEnvelopesDetailsBean.setHeadIcon(userSchedule.getHeadicon());
			reaEnvelopesDetailsBean.setUsername(userSchedule.getNickname());
			ReaEnvelopesDetailsBeanList.add(reaEnvelopesDetailsBean);
		}
		map.put("currency", redEnvelopesRedis.getCurrency());
		map.put("count", redEnvelopes.getCount());
		map.put("useCount", redEnvelopes.getCount()-redEnvelopesRedis.getCount());
		map.put("user", user.getUsername());
		map.put("amount", redEnvelopes.getAmount().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
		map.put("time", redEnvelopes.getCreatetime());
		map.put("list", ReaEnvelopesDetailsBeanList);
		return ReturnInfo.info(200,map);
	}
	
	/**
	 * @info 用户账户操作
	 * @return
	 */ 
	
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("accountOperation")
    public @ResponseBody ReturnInfo accountOperation(String purseAddress, String currency, Double amount, Integer type){
		if(StringUtils.judgeBlank(purseAddress) && StringUtils.judgeBlank(currency) && amount==null){
			return ReturnInfo.error("参数错误！");
		}
		if(type==null || type!=0 && type!=1){
			return ReturnInfo.error("参数错误！");
		}
		List<UserSchedule> userScheduleList = userScheduleService.queryByPurseAddress(purseAddress);
		Long userId = userScheduleList.get(0).getUserid();
		//得到币id
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currency);
		userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, amount, type);
		return ReturnInfo.ok();
	}*/
	
	/**
	 * @info 用户红包记录
	 * @return
	 */ 
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="detailsOfRedEnvelopes", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo detailsOfRedEnvelopes(@RequestBody DetailsOfRedEnvelopesPostBean detailsOfRedEnvelopesPostBean){
		Long userId=detailsOfRedEnvelopesPostBean.getUserId();
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		List<DetailsOfRedEnvelopesBean> detailsOfRedEnvelopesBeanList = new ArrayList<DetailsOfRedEnvelopesBean>();
		List<RedEnvelopes> redEnvelopesList= redEnvelopesService.queryByUserId(userId);
		List<UserRedEnvelopes> userRedEnvelopes = userRedEnvelopesService.queryByUserId(userId);
		for(int i=0;i<redEnvelopesList.size();i++){
			DetailsOfRedEnvelopesBean detailsOfRedEnvelopesBean = new DetailsOfRedEnvelopesBean();
			detailsOfRedEnvelopesBean.setAmount(redEnvelopesList.get(i).getAmount().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfRedEnvelopesBean.setCreateTime(redEnvelopesList.get(i).getCreatetime());
			detailsOfRedEnvelopesBean.setCurrency(redEnvelopesList.get(i).getCurrency());
			detailsOfRedEnvelopesBean.setType(1);	
			UserSchedule userSchedule = userScheduleService.queryByUIdList(redEnvelopesList.get(i).getUserid()).get(0);
			detailsOfRedEnvelopesBean.setHeadIcon(userSchedule.getHeadicon());
			detailsOfRedEnvelopesBean.setUsername(userSchedule.getNickname());
			detailsOfRedEnvelopesBeanList.add(detailsOfRedEnvelopesBean);
		}
		for(int i=0;i<userRedEnvelopes.size();i++){
			DetailsOfRedEnvelopesBean detailsOfRedEnvelopesBean = new DetailsOfRedEnvelopesBean();
			detailsOfRedEnvelopesBean.setAmount(userRedEnvelopes.get(i).getMonery().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfRedEnvelopesBean.setCreateTime(userRedEnvelopes.get(i).getCreatetime());
			RedEnvelopes redEnvelopes = redEnvelopesService.queryRedEnvelopesData(userRedEnvelopes.get(i).getRedenvelopesid());
			detailsOfRedEnvelopesBean.setCurrency(redEnvelopes.getCurrency());
			detailsOfRedEnvelopesBean.setType(0);
			UserSchedule userSchedule = userScheduleService.queryByUIdList(redEnvelopes.getUserid()).get(0);
			detailsOfRedEnvelopesBean.setHeadIcon(userSchedule.getHeadicon());
			detailsOfRedEnvelopesBean.setUsername(userSchedule.getNickname());
			detailsOfRedEnvelopesBeanList.add(detailsOfRedEnvelopesBean);
		}
		for(int i=0;i<detailsOfRedEnvelopesBeanList.size()-1;i++){
			for(int j=0;j<detailsOfRedEnvelopesBeanList.size()-i-1;j++){
				if(detailsOfRedEnvelopesBeanList.get(j).getCreateTime().getTime()<detailsOfRedEnvelopesBeanList.get(j+1).getCreateTime().getTime()){
					DetailsOfRedEnvelopesBean temp=detailsOfRedEnvelopesBeanList.get(j);
					detailsOfRedEnvelopesBeanList.set(j, detailsOfRedEnvelopesBeanList.get(j+1));
					detailsOfRedEnvelopesBeanList.set(j+1, temp);
				}
			}
		}
		return ReturnInfo.info(200, detailsOfRedEnvelopesBeanList);
	}
}
