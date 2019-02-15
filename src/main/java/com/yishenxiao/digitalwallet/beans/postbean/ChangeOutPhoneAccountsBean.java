package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class ChangeOutPhoneAccountsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String currencyName;
	private Double monetary;
	private String phone;
	private String transferDesc;
	private String payPass;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Double getMonetary() {
		return monetary;
	}
	public void setMonetary(Double monetary) {
		this.monetary = monetary;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	@Override
	public String toString() {
		return "ChangeOutPhoneAccountsBean [userId=" + userId + ", currencyName=" + currencyName + ", monetary="
				+ monetary + ", phone=" + phone + ", transferDesc=" + transferDesc + ", payPass=" + payPass + "]";
	}
}
