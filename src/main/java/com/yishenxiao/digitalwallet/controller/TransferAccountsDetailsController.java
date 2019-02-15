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

import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.digitalwallet.beans.DetailsOfTheBill;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.RedEnvelopes;
import com.yishenxiao.digitalwallet.beans.RedEnvelopesReturn;
import com.yishenxiao.digitalwallet.beans.TransferAccountsDetails;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;
import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;
import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;
import com.yishenxiao.digitalwallet.beans.postbean.ChangeOutPhoneAccountsBean;
import com.yishenxiao.digitalwallet.beans.postbean.DetailsOfCoinFillingBean;
import com.yishenxiao.digitalwallet.beans.postbean.JudgingOutOfTheFieldBean;
import com.yishenxiao.digitalwallet.beans.postbean.TransferOutAccountsBean;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.RedEnvelopesReturnService;
import com.yishenxiao.digitalwallet.service.RedEnvelopesService;
import com.yishenxiao.digitalwallet.service.TransferAccountsDetailsService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserRedEnvelopesService;
import com.yishenxiao.digitalwallet.service.UserTransferAccountsDetailsService;
import com.yishenxiao.digitalwallet.service.UserWithdrawCashInfoService;
import com.yishenxiao.digitalwallet.service.WalletThingService;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("transferAccountsDetails")
public class TransferAccountsDetailsController {
	
	@Autowired(required=false)@Qualifier("userTransferAccountsDetailsService")
	private UserTransferAccountsDetailsService userTransferAccountsDetailsService;
    
	@Autowired(required=false)@Qualifier("transferAccountsDetailsService")
    private TransferAccountsDetailsService transferAccountsDetailsService;
	
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
	
	@Autowired(required=false)@Qualifier("redEnvelopesReturnService")
	private RedEnvelopesReturnService redEnvelopesReturnService;
	
	@Autowired(required=false)@Qualifier("userWithdrawCashInfoService")
    private UserWithdrawCashInfoService userWithdrawCashInfoService;
	
	/**
	 * @info 用户账户转入操作，扫一扫， 地址   场内场外
	 * @return
	 */
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="transferOutAccounts", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo transferOutAccounts(@RequestBody TransferOutAccountsBean transferOutAccountsBean){
		return ReturnInfo.error("内测版本，暂不支持转账！");
		String currencyName=transferOutAccountsBean.getCurrencyName();
		String transferOutAddr=transferOutAccountsBean.getTransferOutAddr();
		Long userId=transferOutAccountsBean.getUserId();
		Double monetary=transferOutAccountsBean.getMonetary();
		String transferDesc=transferOutAccountsBean.getTransferDesc();
		String payPass=transferOutAccountsBean.getPayPass();
		if(StringUtils.judgeBlank(currencyName) || StringUtils.judgeBlank(transferOutAddr) || 
				userId==null || monetary==null || StringUtils.judgeBlank(payPass)){
			return ReturnInfo.error("参数错误！");
		}
		UserSchedule userSchedule = userScheduleService.queryByUserId(userId);
		List<UserSchedule> uslist = userScheduleService.queryByPaypw(userSchedule.getId(),new Sha256Hash(payPass).toHex());
		if(uslist.size()!=1){
			return ReturnInfo.error("转账失败！"); 
		}
		if(StringUtils.judgeBlank(transferDesc)){
			transferDesc="转账";
		}
		//return ReturnInfo.error("内测版本，暂不支持转账！");
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
		if(digitalCurrencyInfo==null){
			return ReturnInfo.error("参数错误！");
		}
		Double digitalcurrencybalance = 0.0;
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userId);
		//查询地址,判断场内还是场外
	    List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(transferOutAddr);
		if(userDigitalAddrList.size()==0){
			//暂时关闭向外转账的功能
			return ReturnInfo.error("内测版本，暂不支持向其它钱包转账！");
			UserDigitalAddr userDigitalAddr = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo.getId());
			if(userDigitalAddr==null){
				//获取eth外账的数据  内账+外借+余额差值
				digitalcurrencybalance = userDigitalCurrencyInfo.getBorrowedbalance().doubleValue()+userDigitalCurrencyInfo.getDigitalcurrencybalance().doubleValue();
				//查询用户地址
				DigitalCurrencyInfo digitalCurrencyInfo2 = digitalCurrencyInfoService.queryByDigitalcurrencyname("ETH");
				UserDigitalAddr userDigitalAddr2 = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo2.getId());
				Map<String, Object> paraterMap = new HashMap<String, Object>();
				paraterMap.put("token_address", digitalCurrencyInfo.getDigitalcurrencyaddr());//钱包地址
				paraterMap.put("pub_address", userDigitalAddr2.getCurrencyaddr());//用户地址
				paraterMap.put("token_identifier", currencyName);//币的名字
				String result = HttpClientUtils.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"ethToken/getEthTokenBalance", paraterMap);
				Double balance = Double.parseDouble(result)/Math.pow(10, digitalCurrencyInfo.getTokendecimal());
				if(userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue()<balance){
					digitalcurrencybalance=digitalcurrencybalance+(balance-userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue());
				}else{
					digitalcurrencybalance=digitalcurrencybalance-(userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue()-balance);
				}
			}else{
				digitalcurrencybalance = userDigitalCurrencyInfo.getBorrowedbalance().doubleValue()+userDigitalCurrencyInfo.getDigitalcurrencybalance().doubleValue();
				Map<String, Object> paraterMap = new HashMap<String, Object>();
				paraterMap.put("address", userDigitalAddr.getCurrencyaddr());
				String result = HttpClientUtils.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"eth/getEthBalance", paraterMap);
				Double balance = Double.parseDouble(result);
				if(userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue()<balance){
					digitalcurrencybalance=digitalcurrencybalance+(balance-userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue());
				}else{
					digitalcurrencybalance=digitalcurrencybalance-(userDigitalCurrencyInfo.getForeignaccountbalance().doubleValue()-balance);
				}
			}
		}else{
			digitalcurrencybalance = userDigitalCurrencyInfo.getDigitalcurrencybalance().doubleValue();
		}
		
		if(digitalcurrencybalance<monetary){
			return ReturnInfo.error("余额不足！");
		}
		ReturnInfo info = walletThingService.transferOutAccounts(userId, currencyName, monetary, transferOutAddr, transferDesc);
		return ReturnInfo.info(200, info);
	}*/
	
	/**
	 * @info 用户账户转入操作，手机号  场内
	 * @return
	 */
	 /*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="changeOutPhoneAccounts", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo changeOutPhoneAccounts(@RequestBody ChangeOutPhoneAccountsBean changeOutPhoneAccountsBean){
		return ReturnInfo.error("内测版本，暂不支持转账！");
		Long userId=changeOutPhoneAccountsBean.getUserId();
		 String currencyName=changeOutPhoneAccountsBean.getCurrencyName();
		 Double monetary=changeOutPhoneAccountsBean.getMonetary();
		 String phone=changeOutPhoneAccountsBean.getPhone();
		 String transferDesc=changeOutPhoneAccountsBean.getTransferDesc();
		 String payPass=changeOutPhoneAccountsBean.getPayPass();
		if(StringUtils.judgeBlank(currencyName) || StringUtils.judgeBlank(phone) || userId==null
		      || monetary==null || StringUtils.judgeBlank(payPass)){
			return ReturnInfo.error("参数错误！");
		}
		UserSchedule userSchedule = userScheduleService.queryByUserId(userId);
		List<UserSchedule> uslist = userScheduleService.queryByPaypw(userSchedule.getId(),new Sha256Hash(payPass).toHex());
		if(uslist.size()!=1){
			return ReturnInfo.error("转账失败！"); 
		}
		if(StringUtils.judgeBlank(transferDesc)){
			transferDesc="好友转账";
		}
		
		List<User> userList = userService.queryUser(phone);
		if(userList.size()==0){
			return ReturnInfo.error("参数错误！");
		}
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
		if(digitalCurrencyInfo==null){
			return ReturnInfo.error("参数错误！");
		}
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userId);
		if(userDigitalCurrencyInfo.getDigitalcurrencybalance().doubleValue()<monetary){
			return ReturnInfo.error("余额不足！");
		}
		walletThingService.changeOutPhoneAccounts(userId, currencyName, monetary, phone, transferDesc);
		return ReturnInfo.ok();	
	}*/
	
	
	/**
	 * @info 转入币种数据
	 * @return
	 */
	/*@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("transferIntoAccounts")
    public @ResponseBody ReturnInfo transferIntoAccounts(String userAddr, String currency, Double monetary){
		if(StringUtils.judgeBlank(userAddr) || StringUtils.judgeBlank(currency) || monetary==null){
			return ReturnInfo.error("参数错误！");
		}
		List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(userAddr);
		List<DigitalCurrencyInfo> digitalCurrencyInfoList= digitalCurrencyInfoService.queryByDigitalcurrencynameForList(currency);
		if(userDigitalAddrList.size()==0 || digitalCurrencyInfoList.size()==0){
			return ReturnInfo.error("参数错误！");
		}
		walletThingService.transferIntoAccounts(userAddr, currency, monetary);
		return ReturnInfo.ok();
	}*/
	
	/**
	 * @info 提币充币详情
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="detailsOfCoinFilling", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo detailsOfCoinFilling(@RequestBody DetailsOfCoinFillingBean detailsOfCoinFillingBean){
		Long userId=detailsOfCoinFillingBean.getUserId();
		if(userId==null){
			return ReturnInfo.error("参数错误！");
		}
		List<DetailsOfTheBill> detailsOfTheBillList = new ArrayList<DetailsOfTheBill>();
		List<RedEnvelopes> redEnvelopesList= redEnvelopesService.queryByUserId(userId);
		List<UserRedEnvelopes> userRedEnvelopes = userRedEnvelopesService.queryByUserId(userId);
		List<TransferAccountsDetails> transferAccountsDetailsList  = transferAccountsDetailsService.queryByUserId(userId);
		List<UserTransferAccountsDetails> userTransferAccountsDetailsList = userTransferAccountsDetailsService.queryTransferDataByUserId(userId);
		List<UserTransferAccountsDetails> userTransferPassiveAccountsDetailsList = userTransferAccountsDetailsService.queryTransferPassiveDataByUserId(userId);
		List<UserTransferAccountsDetails> userTransferRechargeAccountsDetailsList = userTransferAccountsDetailsService.queryRechargeDataByUserId(userId);
		List<RedEnvelopesReturn> redEnvelopesReturnList = redEnvelopesReturnService.queryByUserId(userId);
		String hongbaoHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/hbtouxiang.png";
		String outsideHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/outside.png";
		String rechargeHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/recharge.png";
		for(int i=0;i<redEnvelopesList.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(redEnvelopesList.get(i).getCreatetime());
			detailsOfTheBill.setCurrency(redEnvelopesList.get(i).getCurrency());
			detailsOfTheBill.setHeadIcon(hongbaoHead);
			detailsOfTheBill.setMonetary("-"+redEnvelopesList.get(i).getAmount().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("发出 "+redEnvelopesList.get(i).getCurrency()+" 的红包");
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		for(int i=0;i<redEnvelopesReturnList.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(redEnvelopesReturnList.get(i).getUpdatetime());
			DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalCurrencyId(redEnvelopesReturnList.get(i).getDigitalcurrencyinfoid());
			detailsOfTheBill.setCurrency(digitalCurrencyInfo.getDigitalcurrencyname());
			detailsOfTheBill.setHeadIcon(hongbaoHead);
			detailsOfTheBill.setMonetary("+"+redEnvelopesReturnList.get(i).getMonery().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("未领取红包退还余额 ");
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		for(int i=0;i<userRedEnvelopes.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(userRedEnvelopes.get(i).getCreatetime());
			RedEnvelopes redEnvelopes = redEnvelopesService.queryRedEnvelopesData(userRedEnvelopes.get(i).getRedenvelopesid());
			detailsOfTheBill.setCurrency(redEnvelopes.getCurrency());
			UserSchedule userSchedule = userScheduleService.queryByUIdList(redEnvelopes.getUserid()).get(0);
			detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
			detailsOfTheBill.setMonetary("+"+userRedEnvelopes.get(i).getMonery().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("收到 "+userSchedule.getNickname()+" 的红包");
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		for(int i=0;i<transferAccountsDetailsList.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(transferAccountsDetailsList.get(i).getCreatetime());
			DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryById(transferAccountsDetailsList.get(i).getDigitalcurrencyinfoid());
			detailsOfTheBill.setCurrency(digitalCurrencyInfo.getDigitalcurrencyname());
			
			if(transferAccountsDetailsList.get(i).getType()==1){
			  detailsOfTheBill.setMonetary("-"+transferAccountsDetailsList.get(i).getMonetary().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			}else{
			  detailsOfTheBill.setMonetary("+"+transferAccountsDetailsList.get(i).getMonetary().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			}
			detailsOfTheBill.setState(transferAccountsDetailsList.get(i).getState());
			if(transferAccountsDetailsList.get(i).getForm()==1){
				if(transferAccountsDetailsList.get(i).getType()==1){
				   detailsOfTheBill.setMsg("提币");
				}else{
				   detailsOfTheBill.setMsg("充币");
				}
				detailsOfTheBill.setHeadIcon(outsideHead);			
				detailsOfTheBill.setFee(digitalCurrencyInfo.getFee().doubleValue());
				UserDigitalAddr userDigitalAddr = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo.getId());
				detailsOfTheBill.setAddr(userDigitalAddr.getCurrencyaddr());
			}else{			
				if(transferAccountsDetailsList.get(i).getType()==1){		   
				   UserSchedule userSchedule = userScheduleService.queryByUIdList(transferAccountsDetailsList.get(i).getFromuserid()).get(0);
				   
			       detailsOfTheBill.setMsg("转账给 "+userSchedule.getNickname());
				   detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());						   
				}else{
					UserSchedule userSchedule = userScheduleService.queryByUIdList(transferAccountsDetailsList.get(i).getFromuserid()).get(0);
				    detailsOfTheBill.setMsg("来自 "+userSchedule.getNickname()+" 的转账");
					detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());	
				}
			}
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		DetailsOfTheBill detailsOfTheBill = null;
		DigitalCurrencyInfo digitalCurrencyInfo = null;
		UserSchedule userSchedule = null;
		UserSchedule passiveUserSchedule = null;
		if (!userTransferAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				digitalCurrencyInfo = digitalCurrencyInfoService
						.queryById(userTransferAccountsDetails.getDigitalcurrencyinfoid());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("-" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				userSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getUserid()).get(0);
				passiveUserSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getTouserid()).get(0);
				detailsOfTheBill.setMsg("转账给 " + passiveUserSchedule.getNickname());
				detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		if (!userTransferPassiveAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferPassiveAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				digitalCurrencyInfo = digitalCurrencyInfoService
						.queryById(userTransferAccountsDetails.getDigitalcurrencyinfoid());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("+" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				userSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getUserid()).get(0);
				detailsOfTheBill.setMsg("来自 " + userSchedule.getNickname() + " 的转账");
				detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		if (!userTransferRechargeAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferRechargeAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				digitalCurrencyInfo = digitalCurrencyInfoService
						.queryById(userTransferAccountsDetails.getDigitalcurrencyinfoid());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("+" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				detailsOfTheBill.setMsg("充币");
				detailsOfTheBill.setHeadIcon(rechargeHead);
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		for(int i=0;i<detailsOfTheBillList.size()-1;i++){
			for(int j=0;j<detailsOfTheBillList.size()-i-1;j++){
				if(detailsOfTheBillList.get(j).getDate().getTime()<detailsOfTheBillList.get(j+1).getDate().getTime()){
					DetailsOfTheBill temp=detailsOfTheBillList.get(j);
					detailsOfTheBillList.set(j, detailsOfTheBillList.get(j+1));
					detailsOfTheBillList.set(j+1, temp);
				}
			}
		}
		return ReturnInfo.info(200, detailsOfTheBillList);
	}
	
	/**
	 * @info 提币充币详情改进版
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="detailsOfCoinFillingNEW", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo detailsOfCoinFillingNEW(@RequestBody DetailsOfCoinFillingBean detailsOfCoinFillingBean){
		Long userId=detailsOfCoinFillingBean.getUserId();
		String currencyName=detailsOfCoinFillingBean.getCurrency();
		if(userId==null || StringUtils.judgeBlank(currencyName)){
			return ReturnInfo.error("参数错误！");
		}
		List<DetailsOfTheBill> detailsOfTheBillList = new ArrayList<DetailsOfTheBill>();
		List<RedEnvelopes> redEnvelopesList= redEnvelopesService.queryByUserIdAndCurrency(userId, currencyName);
		List<UserRedEnvelopes> userRedEnvelopes = userRedEnvelopesService.queryByUserIdAndCurrency(userId, currencyName);
		DigitalCurrencyInfo digitalCurrencyInfo2 = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
		List<UserTransferAccountsDetails> userTransferAccountsDetailsList = userTransferAccountsDetailsService.queryTransferDataByUserIdAndCurrency(userId,digitalCurrencyInfo2.getId());
		List<UserTransferAccountsDetails> userTransferPassiveAccountsDetailsList = userTransferAccountsDetailsService.queryTransferPassiveDataByUserIdAndCurrency(userId,digitalCurrencyInfo2.getId());
		List<UserTransferAccountsDetails> userTransferRechargeAccountsDetailsList = userTransferAccountsDetailsService.queryRechargeDataByUserIdAndCurrency(userId,digitalCurrencyInfo2.getId());
		List<RedEnvelopesReturn> redEnvelopesReturnList = redEnvelopesReturnService.queryByUserIdAndCurrencyId(userId,digitalCurrencyInfo2.getId());
		String hongbaoHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/hbtouxiang.png";
		String outsideHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/outside.png";
		String rechargeHead="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/recharge.png";
		List<UserWithdrawCashInfo> userWithdrawCashInfoList = userWithdrawCashInfoService.queryByUserIdAndCurrencyId(userId, digitalCurrencyInfo2.getId());
		for(int i=0;i<redEnvelopesList.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(redEnvelopesList.get(i).getCreatetime());
			detailsOfTheBill.setCurrency(redEnvelopesList.get(i).getCurrency());
			detailsOfTheBill.setHeadIcon(hongbaoHead);
			detailsOfTheBill.setMonetary("-"+redEnvelopesList.get(i).getAmount().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("发出 "+redEnvelopesList.get(i).getCurrency()+" 的红包");
			detailsOfTheBill.setFee(0d);
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		for(int i=0;i<redEnvelopesReturnList.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(redEnvelopesReturnList.get(i).getUpdatetime());
			detailsOfTheBill.setCurrency(digitalCurrencyInfo2.getDigitalcurrencyname());
			detailsOfTheBill.setHeadIcon(hongbaoHead);
			detailsOfTheBill.setMonetary("+"+redEnvelopesReturnList.get(i).getMonery().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("未领取红包退还余额 ");
			detailsOfTheBill.setFee(0d);
			detailsOfTheBillList.add(detailsOfTheBill);
		}
		for(int i=0;i<userRedEnvelopes.size();i++){
			DetailsOfTheBill detailsOfTheBill = new DetailsOfTheBill();
			detailsOfTheBill.setDate(userRedEnvelopes.get(i).getCreatetime());
			RedEnvelopes redEnvelopes = redEnvelopesService.queryRedEnvelopesData(userRedEnvelopes.get(i).getRedenvelopesid());
			detailsOfTheBill.setCurrency(redEnvelopes.getCurrency());
			List<UserSchedule> userScheduleList = userScheduleService.queryByUIdList(redEnvelopes.getUserid());
			UserSchedule userSchedule = null;
			if(userScheduleList.size()>0){
				userSchedule=userScheduleList.get(0);
			}else{
				continue;
			}
			detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
			detailsOfTheBill.setMonetary("+"+userRedEnvelopes.get(i).getMonery().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
			detailsOfTheBill.setMsg("收到 "+userSchedule.getNickname()+" 的红包");
			detailsOfTheBill.setFee(0d);
			detailsOfTheBillList.add(detailsOfTheBill);
		}

		DetailsOfTheBill detailsOfTheBill = null;
		UserSchedule userSchedule = null;
		UserSchedule passiveUserSchedule = null;
		if (!userTransferAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo2.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("-" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				userSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getUserid()).get(0);
				passiveUserSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getTouserid()).get(0);
				detailsOfTheBill.setMsg("转账给 " + passiveUserSchedule.getNickname());
				detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		if (!userTransferPassiveAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferPassiveAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo2.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("+" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				userSchedule = userScheduleService.queryByUIdList(userTransferAccountsDetails.getUserid()).get(0);
				detailsOfTheBill.setMsg("来自 " + userSchedule.getNickname() + " 的转账");
				detailsOfTheBill.setHeadIcon(userSchedule.getHeadicon());
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		if (!userTransferRechargeAccountsDetailsList.isEmpty()) {
			for (UserTransferAccountsDetails userTransferAccountsDetails : userTransferRechargeAccountsDetailsList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userTransferAccountsDetails.getCreatetime());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo2.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("+" + subZeroAndDot(userTransferAccountsDetails.getTransfervalue().toPlainString()));
				detailsOfTheBill.setState(userTransferAccountsDetails.getTransferstatus());
				detailsOfTheBill.setMsg("充币");
				detailsOfTheBill.setHeadIcon(rechargeHead);
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}		
		if (userWithdrawCashInfoList.size()!=0) {
			for (UserWithdrawCashInfo userWithdrawCashInfo : userWithdrawCashInfoList) {
				detailsOfTheBill = new DetailsOfTheBill();
				detailsOfTheBill.setDate(userWithdrawCashInfo.getCreatetime());
				detailsOfTheBill.setCurrency(digitalCurrencyInfo2.getDigitalcurrencyname());
				detailsOfTheBill.setMonetary("-" + subZeroAndDot(userWithdrawCashInfo.getWithdrawamount().toPlainString()));
				if(!userWithdrawCashInfo.getHandlestatus()){
				  detailsOfTheBill.setState(0);
				  detailsOfTheBill.setMsg("提现(处理中)");
				}else{
				  detailsOfTheBill.setState(1);
				  detailsOfTheBill.setMsg("提现");
				}	
				detailsOfTheBill.setHeadIcon(outsideHead);
				detailsOfTheBill.setAddr("");
				detailsOfTheBill.setFee(0d);
				detailsOfTheBillList.add(detailsOfTheBill);
			}
		}
		//返回根据时间排序
		for(int i=0;i<detailsOfTheBillList.size()-1;i++){
			for(int j=0;j<detailsOfTheBillList.size()-i-1;j++){
				if(detailsOfTheBillList.get(j).getDate().getTime()<detailsOfTheBillList.get(j+1).getDate().getTime()){
					DetailsOfTheBill temp=detailsOfTheBillList.get(j);
					detailsOfTheBillList.set(j, detailsOfTheBillList.get(j+1));
					detailsOfTheBillList.set(j+1, temp);
				}
			}
		}
		return ReturnInfo.info(200, detailsOfTheBillList);
	}
	
	
	private String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
	
	/**
	 * @info 判断场内场外
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="JudgingOutOfTheField", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo JudgingOutOfTheField(@RequestBody JudgingOutOfTheFieldBean judgingOutOfTheFieldBean){
		String userAddr=judgingOutOfTheFieldBean.getUserAddr();
		List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(userAddr);
		if(userDigitalAddrList.size()>0){
			return ReturnInfo.info(200, true);
		}
		return ReturnInfo.info(200, false);
	}
}
