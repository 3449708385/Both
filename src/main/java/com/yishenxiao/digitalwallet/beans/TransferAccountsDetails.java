package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransferAccountsDetails implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Long digitalcurrencyinfoid;

    private Date createtime;

    private Date updatetime;

    private BigDecimal monetary;

    private Integer type;

    private Integer state;

    private Integer form;

    private Long fromuserid;

    private String transferdesc;

    private String otcstatetransferstatuscode;

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

    public BigDecimal getMonetary() {
        return monetary;
    }

    public void setMonetary(BigDecimal monetary) {
        this.monetary = monetary;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public Long getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Long fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getTransferdesc() {
        return transferdesc;
    }

    public void setTransferdesc(String transferdesc) {
        this.transferdesc = transferdesc == null ? null : transferdesc.trim();
    }

    public String getOtcstatetransferstatuscode() {
        return otcstatetransferstatuscode;
    }

    public void setOtcstatetransferstatuscode(String otcstatetransferstatuscode) {
        this.otcstatetransferstatuscode = otcstatetransferstatuscode == null ? null : otcstatetransferstatuscode.trim();
    }

	@Override
	public String toString() {
		return "TransferAccountsDetails [id=" + id + ", userid=" + userid + ", digitalcurrencyinfoid="
				+ digitalcurrencyinfoid + ", createtime=" + createtime + ", updatetime=" + updatetime + ", monetary="
				+ monetary + ", type=" + type + ", state=" + state + ", form=" + form + ", fromuserid=" + fromuserid
				+ ", transferdesc=" + transferdesc + ", otcstatetransferstatuscode=" + otcstatetransferstatuscode + "]";
	}
}