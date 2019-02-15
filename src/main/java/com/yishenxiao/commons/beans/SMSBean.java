package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class SMSBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;//要发送用户的id
	private String phone;//多个手机号请用半角逗号隔开
	private String smsText;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	@Override
	public String toString() {
		return "SMSBean [userId=" + userId + ", phone=" + phone + ", smsText=" + smsText + "]";
	}
}
