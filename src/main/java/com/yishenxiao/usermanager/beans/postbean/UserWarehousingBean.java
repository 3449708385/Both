package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserWarehousingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String extensionUserId;
	private String account;
	private String nativecode;
	private String phonecode;
	private String geetest_challenge; 
	private String geetest_validate;
	private String geetest_seccode;
	private String type;
	public String getExtensionUserId() {
		return extensionUserId;
	}
	public void setExtensionUserId(String extensionUserId) {
		this.extensionUserId = extensionUserId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNativecode() {
		return nativecode;
	}
	public void setNativecode(String nativecode) {
		this.nativecode = nativecode;
	}
	public String getPhonecode() {
		return phonecode;
	}
	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}
	public String getGeetest_challenge() {
		return geetest_challenge;
	}
	public void setGeetest_challenge(String geetest_challenge) {
		this.geetest_challenge = geetest_challenge;
	}
	public String getGeetest_validate() {
		return geetest_validate;
	}
	public void setGeetest_validate(String geetest_validate) {
		this.geetest_validate = geetest_validate;
	}
	public String getGeetest_seccode() {
		return geetest_seccode;
	}
	public void setGeetest_seccode(String geetest_seccode) {
		this.geetest_seccode = geetest_seccode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "UserWarehousingBean [extensionUserId=" + extensionUserId + ", account=" + account + ", nativecode="
				+ nativecode + ", phonecode=" + phonecode + ", geetest_challenge=" + geetest_challenge
				+ ", geetest_validate=" + geetest_validate + ", geetest_seccode=" + geetest_seccode + ", type=" + type
				+ "]";
	}
}
