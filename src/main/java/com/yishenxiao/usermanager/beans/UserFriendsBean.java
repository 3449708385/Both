package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class UserFriendsBean implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String userName;
   private String phones;
   private String userId;
   private String dataToken;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDataToken() {
		return dataToken;
	}
	public void setDataToken(String dataToken) {
		this.dataToken = dataToken;
	}
	@Override
	public String toString() {
		return "UserFriendsBean [userName=" + userName + ", phones=" + phones + ", userId=" + userId + ", dataToken="
				+ dataToken + "]";
	}
}
