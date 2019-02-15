package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class ForgetToPayThePasswordBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String userName;
    private String paypw;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPaypw() {
		return paypw;
	}
	public void setPaypw(String paypw) {
		this.paypw = paypw;
	}
	@Override
	public String toString() {
		return "ForgetToPayThePasswordBean [userName=" + userName + ", paypw=" + paypw + "]";
	}
}
