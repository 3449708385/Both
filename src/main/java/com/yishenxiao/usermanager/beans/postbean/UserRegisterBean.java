package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserRegisterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String nativecode;
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
	@Override
	public String toString() {
		return "UserRegisterBean [account=" + account + ", nativecode=" + nativecode + "]";
	}
}
