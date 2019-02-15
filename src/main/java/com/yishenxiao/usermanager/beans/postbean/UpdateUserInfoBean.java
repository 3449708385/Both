package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateUserInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String userName;
    private String nickName;
    private String headIcon;
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
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "UpdateUserInfoBean [userName=" + userName + ", nickName=" + nickName + ", headIcon=" + headIcon
				+ ", model=" + model + "]";
	} 
}
