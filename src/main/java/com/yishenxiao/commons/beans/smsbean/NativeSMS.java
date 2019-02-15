package com.yishenxiao.commons.beans.smsbean;

import java.io.Serializable;

public class NativeSMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String account;
    private String password;
    private String msg;
    private String mobile;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "NativeSMS [account=" + account + ", password=" + password + ", msg=" + msg + ", mobile=" + mobile + "]";
	}
}
