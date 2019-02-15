package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetPhoneListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phones;
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	@Override
	public String toString() {
		return "GetPhoneListBean [phones=" + phones + "]";
	}
}
