package com.yishenxiao.digitalwallet.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.digitalwallet.beans.CoinsList;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;
import com.yishenxiao.digitalwallet.beans.postbean.UserWithdrawCashBean;
import com.yishenxiao.digitalwallet.service.CoinsListService;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserWithdrawCashInfoService;
import com.yishenxiao.digitalwallet.service.WalletThingService;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.UserScheduleService;

@Controller
@RequestMapping("outSide")
public class UserOutSideController {

	@Autowired(required = false)
	@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;

	@Autowired(required = false)
	@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;

	@Autowired(required = false)
	@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;

	@Autowired(required = false)
	@Qualifier("userDigitalAddrService")
	private UserDigitalAddrService userDigitalAddrService;

	@Autowired(required = false)
	@Qualifier("userWithdrawCashInfoService")
	private UserWithdrawCashInfoService userWithdrawCashInfoService;

	@Autowired(required = false)
	@Qualifier("walletThingService")
	private WalletThingService walletThingService;

	@Autowired(required = false)
	@Qualifier("coinsListService")
	private CoinsListService coinsListService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "userWithdrawCash", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo userWithdrawCash(@RequestBody UserWithdrawCashBean userWithdrawCashBean) {
		return ReturnInfo.error("内测版本，暂不支持向其他钱包转账！");
		/*String userId = userWithdrawCashBean.getUserId();
		String currencyName = userWithdrawCashBean.getCurrencyName();
		String userCurrencyAddr = userWithdrawCashBean.getUserCurrencyAddr();
		String withdrawAddr = userWithdrawCashBean.getWithdrawAddr();
		BigDecimal withdrawAmount = userWithdrawCashBean.getWithdrawAmount();
		String remark = userWithdrawCashBean.getRemark();
		String payPass = userWithdrawCashBean.getPayPass();
		String certKey = userWithdrawCashBean.getCertKey();
		// 校验空字段
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(currencyName) || StringUtils.isEmpty(userCurrencyAddr)
				|| StringUtils.isEmpty(withdrawAddr) || withdrawAmount == null || StringUtils.isEmpty(payPass)
				|| StringUtils.isEmpty(certKey)) {
			return ReturnInfo.error("参数错误！");
		}
		// 校验货币名称
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
		if (digitalCurrencyInfo == null) {
			return ReturnInfo.error("参数错误！");
		}
		// 校验用户id
		UserSchedule userSchedule = userScheduleService.queryByUIdList(Long.parseLong(userId)).get(0);
		if (userSchedule == null) {
			return ReturnInfo.error("参数错误！");
		}
		// 异常用户不能提现
		List<CoinsList> coinsList = coinsListService.queryByUserId(Long.parseLong(userId));
		if (coinsList.size() > 0) {
			return ReturnInfo.error("该账户存在违规操作，请联系客服！");
		}
		// 校验用户数字货币地址
		UserDigitalAddr userDigitalAddr = null;
		DigitalCurrencyInfo superDigitalCurrencyInfo = null;
		if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
			superDigitalCurrencyInfo = digitalCurrencyInfoService
					.queryById(digitalCurrencyInfo.getDigitalcurrencyinfoid());
			userDigitalAddr = userDigitalAddrService.queryByData(Long.parseLong(userId),
					superDigitalCurrencyInfo.getId());
		} else {
			userDigitalAddr = userDigitalAddrService.queryByData(Long.parseLong(userId), digitalCurrencyInfo.getId());
		}
		if (userDigitalAddr == null) {
			return ReturnInfo.error("参数错误！");
		}
		// 比对钱包地址
		if (userCurrencyAddr.equals(withdrawAddr)) {
			return ReturnInfo.error("钱包地址不正确！");
		}
		// 校验certKey
		String topFive = userCurrencyAddr.substring(2, 7);
		String afterFive = userCurrencyAddr.substring(userCurrencyAddr.length() - 5, userCurrencyAddr.length());
		List<UserWithdrawCashInfo> userWithdrawCashInfoList = userWithdrawCashInfoService
				.queryByUserId(Long.parseLong(userId));
		String serverCertKey = new Sha256Hash(
				afterFive + PropertiesUtils.getInfoConfigProperties().getProperty("digitalCurrency.fixed.encryptParam")
						+ topFive + userWithdrawCashInfoList.size()).toString();
		if (!certKey.equals(serverCertKey)) {
			return ReturnInfo.error("参数错误！");
		}
		// 校验支付密码
		List<UserSchedule> userList = userScheduleService.queryByPaypw(userSchedule.getId(),
				new Sha256Hash(payPass).toHex());
		if (userList.size() != 1) {
			return ReturnInfo.error("参数错误！");
		}
		// 判断提现金额是否是负数
		if (withdrawAmount.compareTo(BigDecimal.ZERO) != 1) {
			return ReturnInfo.error("输入金额有误！");
		}
		// 判断用户余额是否足够提现
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService
				.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), Long.parseLong(userId));
		if (userDigitalCurrencyInfo == null) {
			return ReturnInfo.error("参数错误！");
		}
		if (userDigitalCurrencyInfo.getDigitalcurrencybalance()
				.compareTo(digitalCurrencyInfo.getFee().add(withdrawAmount)) < 0) {
			return ReturnInfo.error("余额不足！");
		}
		return walletThingService.userWithdrawCash(Long.parseLong(userId), digitalCurrencyInfo,
				superDigitalCurrencyInfo, userDigitalCurrencyInfo, withdrawAddr, withdrawAmount, remark);*/
	}

}