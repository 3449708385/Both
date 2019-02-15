package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserTransferBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 发起转账的用户id
	private String userId;
	// 转账货币名称
	private String currencyName;
	// 用户账户地址
	private String userCurrencyAddr;
	// 转账地址
	private String transferAddr;
	// 用户手机号
	private String userMobile;
	// 转账金额
	private BigDecimal transferValue;
	// 转账备注
	private String transferDesc;
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

	public String getTransferAddr() {
		return transferAddr;
	}

	public void setTransferAddr(String transferAddr) {
		this.transferAddr = transferAddr;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public BigDecimal getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(BigDecimal transferValue) {
		this.transferValue = transferValue;
	}

	public String getTransferDesc() {
		return transferDesc;
	}

	public void setTransferDesc(String transferDesc) {
		this.transferDesc = transferDesc;
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
		return "UserTransferBean [userId=" + userId + ", currencyName=" + currencyName + ", userCurrencyAddr="
				+ userCurrencyAddr + ", transferAddr=" + transferAddr + ", userMobile=" + userMobile
				+ ", transferValue=" + transferValue + ", transferDesc=" + transferDesc + ", payPass=" + payPass
				+ ", certKey=" + certKey + "]";
	}

}
