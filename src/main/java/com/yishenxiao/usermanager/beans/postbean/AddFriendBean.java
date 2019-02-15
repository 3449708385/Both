package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class AddFriendBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String userName;
    private String phones;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	@Override
	public String toString() {
		return "AddFriendBean [userName=" + userName + ", phones=" + phones + "]";
	}
}
