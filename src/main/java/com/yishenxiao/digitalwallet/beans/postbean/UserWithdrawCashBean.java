package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserWithdrawCashBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 发起提现的用户id
	private String userId;
	// 提现货币名称
	private String currencyName;
	// 用户账户地址
	private String userCurrencyAddr;
	// 提现地址
	private String withdrawAddr;
	// 提现金额
	private BigDecimal withdrawAmount;
	// 提现备注
	private String remark;
	// 支付密码
	private String payPass;
	// 加密校验
	private String certKey;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getUserCurrencyAddr() {
		return userCurrencyAddr;
	}

	public void setUserCurrencyAddr(String userCurrencyAddr) {
		this.userCurrencyAddr = userCurrencyAddr;
	}

	public String getWithdrawAddr() {
		return withdrawAddr;
	}

	public void setWithdrawAddr(String withdrawAddr) {
		this.withdrawAddr = withdrawAddr;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayPass() {
		return payPass;
	}

	public void setPayPass(String payPass) {
		this.payPass = payPass;
	}

	public String getCertKey() {
		return certKey;
	}

	public void setCertKey(String certKey) {
		this.certKey = certKey;
	}

	@Override
	public String toString() {
		return "UserWithdrawCashBean [userId=" + userId + ", currencyName=" + currencyName + ", userCurrencyAddr="
				+ userCurrencyAddr + ", withdrawAddr=" + withdrawAddr + ", withdrawAmount=" + withdrawAmount
				+ ", remark=" + remark + ", payPass=" + payPass + ", certKey=" + certKey + "]";
	}

}
