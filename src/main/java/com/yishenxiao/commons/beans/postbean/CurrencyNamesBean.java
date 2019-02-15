package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class CurrencyNamesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CurrencyNamesBean [userId=" + userId + "]";
	}

}
