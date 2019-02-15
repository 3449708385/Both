package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RealTransferAccountsDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userid;

	private Long digitalcurrencyinfoid;

	private Short transfertype;

	private String transferaddr;

	private String receiptaddr;

	private String transfertxhash;

	private Long gasupperlimit;

	private String gasprice;

	private Long gasused;

	private BigDecimal transfervalue;

	private BigDecimal realtransferfee;

	private Short transferprogress;

	private Boolean handlestatus;

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

	public Short getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(Short transfertype) {
		this.transfertype = transfertype;
	}

	public String getTransferaddr() {
		return transferaddr;
	}

	public void setTransferaddr(String transferaddr) {
		this.transferaddr = transferaddr == null ? null : transferaddr.trim();
	}

	public String getReceiptaddr() {
		return receiptaddr;
	}

	public void setReceiptaddr(String receiptaddr) {
		this.receiptaddr = receiptaddr == null ? null : receiptaddr.trim();
	}

	public String getTransfertxhash() {
		return transfertxhash;
	}

	public void setTransfertxhash(String transfertxhash) {
		this.transfertxhash = transfertxhash == null ? null : transfertxhash.trim();
	}

	public Long getGasupperlimit() {
		return gasupperlimit;
	}

	public void setGasupperlimit(Long gasupperlimit) {
		this.gasupperlimit = gasupperlimit;
	}

	public String getGasprice() {
		return gasprice;
	}

	public void setGasprice(String gasprice) {
		this.gasprice = gasprice == null ? null : gasprice.trim();
	}

	public Long getGasused() {
		return gasused;
	}

	public void setGasused(Long gasused) {
		this.gasused = gasused;
	}

	public BigDecimal getTransfervalue() {
		return transfervalue;
	}

	public void setTransfervalue(BigDecimal transfervalue) {
		this.transfervalue = transfervalue;
	}

	public BigDecimal getRealtransferfee() {
		return realtransferfee;
	}

	public void setRealtransferfee(BigDecimal realtransferfee) {
		this.realtransferfee = realtransferfee;
	}

	public Short getTransferprogress() {
		return transferprogress;
	}

	public void setTransferprogress(Short transferprogress) {
		this.transferprogress = transferprogress;
	}

	public Boolean getHandlestatus() {
		return handlestatus;
	}

	public void setHandlestatus(Boolean handlestatus) {
		this.handlestatus = handlestatus;
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
		return "RealTransferAccountsDetails [id=" + id + ", userid=" + userid + ", digitalcurrencyinfoid="
				+ digitalcurrencyinfoid + ", transfertype=" + transfertype + ", transferaddr=" + transferaddr
				+ ", receiptaddr=" + receiptaddr + ", transfertxhash=" + transfertxhash + ", gasupperlimit="
				+ gasupperlimit + ", gasprice=" + gasprice + ", gasused=" + gasused + ", transfervalue=" + transfervalue
				+ ", realtransferfee=" + realtransferfee + ", transferprogress=" + transferprogress + ", handlestatus="
				+ handlestatus + ", createtime=" + createtime + ", updatetime=" + updatetime + ", enabled=" + enabled
				+ ", deleted=" + deleted + "]";
	}

}