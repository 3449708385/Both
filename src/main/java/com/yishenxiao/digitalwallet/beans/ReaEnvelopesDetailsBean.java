package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.util.Date;

public class ReaEnvelopesDetailsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String username;
    private Double monery;
    private String headIcon;
    private Date createTime;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getMonery() {
		return monery;
	}
	public void setMonery(Double monery) {
		this.monery = monery;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	@Override
	public String toString() {
		return "ReaEnvelopesDetailsBean [username=" + username + ", monery=" + monery + ", headIcon=" + headIcon
				+ ", createTime=" + createTime + "]";
	}
	
}
