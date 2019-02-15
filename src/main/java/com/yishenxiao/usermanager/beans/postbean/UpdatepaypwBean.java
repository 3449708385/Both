package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdatepaypwBean implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String userName;
   private String oldpaypw;
   private String paypw;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldpaypw() {
		return oldpaypw;
	}
	public void setOldpaypw(String oldpaypw) {
		this.oldpaypw = oldpaypw;
	}
	public String getPaypw() {
		return paypw;
	}
	public void setPaypw(String paypw) {
		this.paypw = paypw;
	}
	@Override
	public String toString() {
		return "UpdatepaypwBean [userName=" + userName + ", oldpaypw=" + oldpaypw + ", paypw=" + paypw + "]";
	}
   
}
