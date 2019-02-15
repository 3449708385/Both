package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetUserGroupDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String userName;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "GetUserGroupDataBean [userId=" + userId + ", userName=" + userName + "]";
	}
}
