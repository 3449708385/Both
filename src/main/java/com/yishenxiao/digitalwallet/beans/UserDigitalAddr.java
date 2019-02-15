package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.util.Date;

public class UserDigitalAddr implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Date createtime;

    private Long digitalcurrencyinfoid;

    private String currencyaddr;

    private Date updatetime;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getDigitalcurrencyinfoid() {
        return digitalcurrencyinfoid;
    }

    public void setDigitalcurrencyinfoid(Long digitalcurrencyinfoid) {
        this.digitalcurrencyinfoid = digitalcurrencyinfoid;
    }

    public String getCurrencyaddr() {
        return currencyaddr;
    }

    public void setCurrencyaddr(String currencyaddr) {
        this.currencyaddr = currencyaddr == null ? null : currencyaddr.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	@Override
	public String toString() {
		return "UserDigitalAddr [id=" + id + ", userid=" + userid + ", createtime=" + createtime
				+ ", digitalcurrencyinfoid=" + digitalcurrencyinfoid + ", currencyaddr=" + currencyaddr
				+ ", updatetime=" + updatetime + "]";
	} 
}