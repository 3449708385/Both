package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class VerificationCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String challenge;
	private String gt;
	private String success;
	private boolean new_captcha;
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public String getGt() {
		return gt;
	}
	public void setGt(String gt) {
		this.gt = gt;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public boolean getNew_captcha() {
		return new_captcha;
	}
	public void setNew_captcha(boolean new_captcha) {
		this.new_captcha = new_captcha;
	}
	@Override
	public String toString() {
		return "VerificationCodeBean [challenge=" + challenge + ", gt=" + gt + ", success=" + success + ", new_captcha="
				+ new_captcha + "]";
	}
}
