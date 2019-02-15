package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserTransferAccountsDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userid;

	private Long digitalcurrencyinfoid;

	private Long touserid;

	private Short transfertype;

	private BigDecimal transfervalue;

	private Short transferstatus;

	private String remark;

	private Date createtime;

	private Date updatetime;

	private Boolean enabled;

	private Boolean deleted;

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

	public Long getDigitalcurrencyinfoid() {
		return digitalcurrencyinfoid;
	}

	public void setDigitalcurrencyinfoid(Long digitalcurrencyinfoid) {
		this.digitalcurrencyinfoid = digitalcurrencyinfoid;
	}

	public Long getTouserid() {
		return touserid;
	}

	public void setTouserid(Long touserid) {
		this.touserid = touserid;
	}

	public Short getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(Short transfertype) {
		this.transfertype = transfertype;
	}

	public BigDecimal getTransfervalue() {
		return transfervalue;
	}

	public void setTransfervalue(BigDecimal transfervalue) {
		this.transfervalue = transfervalue;
	}

	public Short getTransferstatus() {
		return transferstatus;
	}

	public void setTransferstatus(Short transferstatus) {
		this.transferstatus = transferstatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "UserTransferAccountsDetails [id=" + id + ", userid=" + userid + ", digitalcurrencyinfoid="
				+ digitalcurrencyinfoid + ", touserid=" + touserid + ", transfertype=" + transfertype
				+ ", transfervalue=" + transfervalue + ", transferstatus=" + transferstatus + ", remark=" + remark
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ", enabled=" + enabled + ", deleted="
				+ deleted + "]";
	}

}