package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserPhoneCodeVerificationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String nativecode;
	private String phonecode;
	private Integer type;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNativecode() {
		return nativecode;
	}
	public void setNativecode(String nativecode) {
		this.nativecode = nativecode;
	}
	public String getPhonecode() {
		return phonecode;
	}
	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "UserPhoneCodeVerificationBean [account=" + account + ", nativecode=" + nativecode + ", phonecode="
				+ phonecode + ", type=" + type + "]";
	}
}
