package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class PaymentPasswordCheckBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String payPassword;
	private Long userId;
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "PaymentPasswordCheckBean [payPassword=" + payPassword + ", userId=" + userId + "]";
	}
}
