package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class GetInstallCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phone;
	private String type;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "GetInstallCodeBean [phone=" + phone + ", type=" + type + "]";
	}
}
