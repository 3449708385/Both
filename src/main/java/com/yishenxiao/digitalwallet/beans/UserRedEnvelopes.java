package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserRedEnvelopes implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date createtime;

    private Date updatetime;

    private Long userid;

    private Long redenvelopesid;

    private BigDecimal monery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getRedenvelopesid() {
        return redenvelopesid;
    }

    public void setRedenvelopesid(Long redenvelopesid) {
        this.redenvelopesid = redenvelopesid;
    }

    public BigDecimal getMonery() {
        return monery;
    }

    public void setMonery(BigDecimal monery) {
        this.monery = monery;
    }

	@Override
	public String toString() {
		return "UserRedEnvelopes [id=" + id + ", createtime=" + createtime + ", updatetime=" + updatetime + ", userid="
				+ userid + ", redenvelopesid=" + redenvelopesid + ", monery=" + monery + "]";
	}
}