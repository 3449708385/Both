package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class UsersRedPacketsDetailsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long redEnvelopesId;
	private String dataKey;
	public Long getRedEnvelopesId() {
		return redEnvelopesId;
	}
	public void setRedEnvelopesId(Long redEnvelopesId) {
		this.redEnvelopesId = redEnvelopesId;
	}
	public String getDataKey() {
		return dataKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	@Override
	public String toString() {
		return "UsersRedPacketsDetailsBean [redEnvelopesId=" + redEnvelopesId + ", dataKey=" + dataKey + "]";
	}
}
