package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class SignUpBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;
	private UserSchedule userSchedule;
	private Boolean userLogin;
	private String accessToken;

	public Boolean getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(Boolean userLogin) {
		this.userLogin = userLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserSchedule getUserSchedule() {
		return userSchedule;
	}

	public void setUserSchedule(UserSchedule userSchedule) {
		this.userSchedule = userSchedule;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "SignUpBean [user=" + user + ", userSchedule=" + userSchedule + ", userLogin=" + userLogin
				+ ", accessToken=" + accessToken + "]";
	}

}
