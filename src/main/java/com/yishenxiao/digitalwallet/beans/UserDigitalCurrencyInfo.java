package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserDigitalCurrencyInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Long digitalcurrencyid;

    private BigDecimal digitalcurrencybalance;

    private BigDecimal foreignaccountbalance;

    private BigDecimal borrowedbalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getDigitalcurrencyid() {
        return digitalcurrencyid;
    }

    public void setDigitalcurrencyid(Long digitalcurrencyid) {
        this.digitalcurrencyid = digitalcurrencyid;
    }

    public BigDecimal getDigitalcurrencybalance() {
        return digitalcurrencybalance;
    }

    public void setDigitalcurrencybalance(BigDecimal digitalcurrencybalance) {
        this.digitalcurrencybalance = digitalcurrencybalance;
    }

    public BigDecimal getForeignaccountbalance() {
        return foreignaccountbalance;
    }

    public void setForeignaccountbalance(BigDecimal foreignaccountbalance) {
        this.foreignaccountbalance = foreignaccountbalance;
    }

    public BigDecimal getBorrowedbalance() {
        return borrowedbalance;
    }

    public void setBorrowedbalance(BigDecimal borrowedbalance) {
        this.borrowedbalance = borrowedbalance;
    }

	@Override
	public String toString() {
		return "UserDigitalCurrencyInfo [id=" + id + ", userid=" + userid + ", digitalcurrencyid=" + digitalcurrencyid
				+ ", digitalcurrencybalance=" + digitalcurrencybalance + ", foreignaccountbalance="
				+ foreignaccountbalance + ", borrowedbalance=" + borrowedbalance + "]";
	}
}