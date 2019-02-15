package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;

public class EasemobUserSend  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userIco;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIco() {
		return userIco;
	}
	public void setUserIco(String userIco) {
		this.userIco = userIco;
	}
	@Override
	public String toString() {
		return "{ userName : " + userName + ", userIco : " + userIco + " }";
	}
    
}
