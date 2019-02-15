package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class JudgingOutOfTheFieldBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userAddr;
	public String getUserAddr() {
		return userAddr;
	}
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}
	@Override
	public String toString() {
		return "JudgingOutOfTheFieldBean [userAddr=" + userAddr + "]";
	}
}
