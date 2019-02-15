package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RedEnvelopesReturn implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Long digitalcurrencyinfoid;

    private Long fromuserid;

    private Long redenvelopesid;

    private Integer redcount;

    private BigDecimal monery;

    private Integer state;

    private Date createtime;

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

    public Long getDigitalcurrencyinfoid() {
        return digitalcurrencyinfoid;
    }

    public void setDigitalcurrencyinfoid(Long digitalcurrencyinfoid) {
        this.digitalcurrencyinfoid = digitalcurrencyinfoid;
    }

    public Long getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Long fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Long getRedenvelopesid() {
        return redenvelopesid;
    }

    public void setRedenvelopesid(Long redenvelopesid) {
        this.redenvelopesid = redenvelopesid;
    }

    public Integer getRedcount() {
        return redcount;
    }

    public void setRedcount(Integer redcount) {
        this.redcount = redcount;
    }

    public BigDecimal getMonery() {
        return monery;
    }

    public void setMonery(BigDecimal monery) {
        this.monery = monery;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

	@Override
	public String toString() {
		return "RedEnvelopesReturn [id=" + id + ", userid=" + userid + ", digitalcurrencyinfoid="
				+ digitalcurrencyinfoid + ", fromuserid=" + fromuserid + ", redenvelopesid=" + redenvelopesid
				+ ", redcount=" + redcount + ", monery=" + monery + ", state=" + state + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + "]";
	}
}