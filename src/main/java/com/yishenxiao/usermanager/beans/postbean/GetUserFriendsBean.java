package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetUserFriendsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "GetUserFriendsBean [userName=" + userName + "]";
	}
}
