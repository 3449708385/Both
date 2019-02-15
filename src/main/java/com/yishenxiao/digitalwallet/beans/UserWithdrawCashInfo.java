package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserWithdrawCashInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userid;

	private Long digitalcurrencyinfoid;

	private Long realtransferaccountsid;

	private String withdrawaddr;

	private BigDecimal withdrawamount;

	private BigDecimal withdrawfee;

	private Short withdrawprogress;

	private Boolean handlestatus;

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

	public Long getRealtransferaccountsid() {
		return realtransferaccountsid;
	}

	public void setRealtransferaccountsid(Long realtransferaccountsid) {
		this.realtransferaccountsid = realtransferaccountsid;
	}

	public String getWithdrawaddr() {
		return withdrawaddr;
	}

	public void setWithdrawaddr(String withdrawaddr) {
		this.withdrawaddr = withdrawaddr == null ? null : withdrawaddr.trim();
	}

	public BigDecimal getWithdrawamount() {
		return withdrawamount;
	}

	public void setWithdrawamount(BigDecimal withdrawamount) {
		this.withdrawamount = withdrawamount;
	}

	public BigDecimal getWithdrawfee() {
		return withdrawfee;
	}

	public void setWithdrawfee(BigDecimal withdrawfee) {
		this.withdrawfee = withdrawfee;
	}

	public Short getWithdrawprogress() {
		return withdrawprogress;
	}

	public void setWithdrawprogress(Short withdrawprogress) {
		this.withdrawprogress = withdrawprogress;
	}

	public Boolean getHandlestatus() {
		return handlestatus;
	}

	public void setHandlestatus(Boolean handlestatus) {
		this.handlestatus = handlestatus;
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
		return "UserWithdrawCashInfo [id=" + id + ", userid=" + userid + ", digitalcurrencyinfoid="
				+ digitalcurrencyinfoid + ", realtransferaccountsid=" + realtransferaccountsid + ", withdrawaddr="
				+ withdrawaddr + ", withdrawamount=" + withdrawamount + ", withdrawfee=" + withdrawfee
				+ ", withdrawprogress=" + withdrawprogress + ", handlestatus=" + handlestatus + ", remark=" + remark
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ", enabled=" + enabled + ", deleted="
				+ deleted + "]";
	}

}