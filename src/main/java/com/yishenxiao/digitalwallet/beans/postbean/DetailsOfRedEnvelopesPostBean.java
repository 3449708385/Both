package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class DetailsOfRedEnvelopesPostBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "DetailsOfRedEnvelopesBean [userId=" + userId + "]";
	} 
}
