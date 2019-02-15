package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateNicknameBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String nickName;
	private String model;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "UpdateNicknameBean [userName=" + userName + ", nickName=" + nickName + ", model=" + model + "]";
	}
}
