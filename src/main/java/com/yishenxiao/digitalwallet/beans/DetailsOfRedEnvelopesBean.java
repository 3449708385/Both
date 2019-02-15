package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DetailsOfRedEnvelopesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private Date createTime;
	private int type;
	private String amount;
	private String currency;
	private String headIcon;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	@Override
	public String toString() {
		return "DetailsOfRedEnvelopesBean [username=" + username + ", createTime=" + createTime + ", type=" + type
				+ ", amount=" + amount + ", currency=" + currency + ", headIcon=" + headIcon + "]";
	}
}
