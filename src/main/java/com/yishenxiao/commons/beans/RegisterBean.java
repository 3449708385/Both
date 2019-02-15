package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class RegisterBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
    public String password;
    public String nickname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "RegisterBean [username=" + username + ", password=" + password + ", nickname=" + nickname + "]";
	}
}
