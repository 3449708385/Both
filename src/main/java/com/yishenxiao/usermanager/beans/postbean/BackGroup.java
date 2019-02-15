package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class BackGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobGroupId;
	private String userName;
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "BackGroup [easemobGroupId=" + easemobGroupId + ", userName=" + userName + "]";
	}
}
