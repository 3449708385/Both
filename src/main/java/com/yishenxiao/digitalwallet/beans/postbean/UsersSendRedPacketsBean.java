package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class UsersSendRedPacketsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String REDesc;
	private String currency; 
	private Double amount;
	private Integer count;
	private String payPass;
	public Long getUserId() {
		return userId;
	}
	public String getPayPass() {
		return payPass;
	}
	public void setPayPass(String payPass) {
		this.payPass = payPass;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getREDesc() {
		return REDesc;
	}
	public void setREDesc(String rEDesc) {
		REDesc = rEDesc;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "UsersSendRedPacketsBean [userId=" + userId + ", REDesc=" + REDesc + ", currency=" + currency
				+ ", amount=" + amount + ", count=" + count + ", payPass=" + payPass + "]";
	}
}
