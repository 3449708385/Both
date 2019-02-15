package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class DigitalCurrencyInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String digitalcurrencyname;

	private String digitalcurrencyaddr;

	private Long digitalcurrencyinfoid;

	private BigDecimal fee;

	private Integer tokendecimal;

	private BigDecimal realbalancelimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDigitalcurrencyname() {
		return digitalcurrencyname;
	}

	public void setDigitalcurrencyname(String digitalcurrencyname) {
		this.digitalcurrencyname = digitalcurrencyname == null ? null : digitalcurrencyname.trim();
	}

	public String getDigitalcurrencyaddr() {
		return digitalcurrencyaddr;
	}

	public void setDigitalcurrencyaddr(String digitalcurrencyaddr) {
		this.digitalcurrencyaddr = digitalcurrencyaddr == null ? null : digitalcurrencyaddr.trim();
	}

	public Long getDigitalcurrencyinfoid() {
		return digitalcurrencyinfoid;
	}

	public void setDigitalcurrencyinfoid(Long digitalcurrencyinfoid) {
		this.digitalcurrencyinfoid = digitalcurrencyinfoid;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getTokendecimal() {
		return tokendecimal;
	}

	public void setTokendecimal(Integer tokendecimal) {
		this.tokendecimal = tokendecimal;
	}

	public BigDecimal getRealbalancelimit() {
		return realbalancelimit;
	}

	public void setRealbalancelimit(BigDecimal realbalancelimit) {
		this.realbalancelimit = realbalancelimit;
	}

	@Override
	public String toString() {
		return "DigitalCurrencyInfo [id=" + id + ", digitalcurrencyname=" + digitalcurrencyname
				+ ", digitalcurrencyaddr=" + digitalcurrencyaddr + ", digitalcurrencyinfoid=" + digitalcurrencyinfoid
				+ ", fee=" + fee + ", tokendecimal=" + tokendecimal + ", realbalancelimit=" + realbalancelimit + "]";
	}

}