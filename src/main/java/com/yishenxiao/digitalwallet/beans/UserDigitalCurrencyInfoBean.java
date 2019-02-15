package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserDigitalCurrencyInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private String digitalcurrency;

    private String digitalcurrencybalance;
    
    private String moneyAddr;
    
    private Double fee;
    
    private String filePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getDigitalcurrency() {
		return digitalcurrency;
	}

	public void setDigitalcurrency(String digitalcurrency) {
		this.digitalcurrency = digitalcurrency;
	}

	public String getDigitalcurrencybalance() {
		return digitalcurrencybalance;
	}

	public void setDigitalcurrencybalance(String digitalcurrencybalance) {
		this.digitalcurrencybalance = digitalcurrencybalance;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMoneyAddr() {
		return moneyAddr;
	}

	public void setMoneyAddr(String moneyAddr) {
		this.moneyAddr = moneyAddr;
	}

	@Override
	public String toString() {
		return "UserDigitalCurrencyInfoBean [id=" + id + ", userid=" + userid + ", digitalcurrency=" + digitalcurrency
				+ ", digitalcurrencybalance=" + digitalcurrencybalance + ", moneryAddr=" + moneyAddr + ", fee=" + fee
				+ ", filePath=" + filePath + "]";
	}

}
