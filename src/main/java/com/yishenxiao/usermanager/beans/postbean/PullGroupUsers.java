package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class PullGroupUsers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String easemobId;
	private String userNames;
	private String account;
	private String userName;
	private String otherUserName;
	public String getEasemobId() {
		return easemobId;
	}
	public void setEasemobId(String easemobId) {
		this.easemobId = easemobId;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOtherUserName() {
		return otherUserName;
	}
	public void setOtherUserName(String otherUserName) {
		this.otherUserName = otherUserName;
	}
	@Override
	public String toString() {
		return "PullGroupUsers [easemobId=" + easemobId + ", userNames=" + userNames + ", account=" + account
				+ ", userName=" + userName + ", otherUserName=" + otherUserName + "]";
	}
}
