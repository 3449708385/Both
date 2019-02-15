package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class TransferOutAccountsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String currencyName;
	private Double monetary;
	private String transferOutAddr;
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
	public String getTransferOutAddr() {
		return transferOutAddr;
	}
	public void setTransferOutAddr(String transferOutAddr) {
		this.transferOutAddr = transferOutAddr;
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
		return "TransferOutAccountsBean [userId=" + userId + ", currencyName=" + currencyName + ", monetary=" + monetary
				+ ", transferOutAddr=" + transferOutAddr + ", transferDesc=" + transferDesc + ", payPass=" + payPass
				+ "]";
	}
}
