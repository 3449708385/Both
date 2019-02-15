package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetBatchUserDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobIds;
	public String getEasemobIds() {
		return easemobIds;
	}
	public void setEasemobIds(String easemobIds) {
		this.easemobIds = easemobIds;
	}
	@Override
	public String toString() {
		return "GetBatchUserDataBean [easemobIds=" + easemobIds + "]";
	}
}
