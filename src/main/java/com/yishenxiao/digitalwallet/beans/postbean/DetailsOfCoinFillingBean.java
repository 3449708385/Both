package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class DetailsOfCoinFillingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long userId;
    private String currency;
	public Long getUserId() {
		return userId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "DetailsOfCoinFillingBean [userId=" + userId + ", currency=" + currency + "]";
	}
}
