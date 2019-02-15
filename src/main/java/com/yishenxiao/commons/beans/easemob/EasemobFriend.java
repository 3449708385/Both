package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;

public class EasemobFriend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String userName;
    private String friendName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	@Override
	public String toString() {
		return "EasemobFriend [userName=" + userName + ", friendName=" + friendName + "]";
	}
}
