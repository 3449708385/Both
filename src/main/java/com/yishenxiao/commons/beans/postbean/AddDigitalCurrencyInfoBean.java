package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class AddDigitalCurrencyInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String digitalCurrencyName;
	public String getDigitalCurrencyName() {
		return digitalCurrencyName;
	}
	public void setDigitalCurrencyName(String digitalCurrencyName) {
		this.digitalCurrencyName = digitalCurrencyName;
	}
	@Override
	public String toString() {
		return "AddDigitalCurrencyInfoBean [digitalCurrencyName=" + digitalCurrencyName + "]";
	}
}
