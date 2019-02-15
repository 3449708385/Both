package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RedEnvelopes implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private String currency;

    private BigDecimal amount;

    private String redesc;

    private Integer count;

    private Integer state;

    private Date createtime;

    private Date updatetime;

    private Integer usecount;

    private String rediskey;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRedesc() {
        return redesc;
    }

    public void setRedesc(String redesc) {
        this.redesc = redesc == null ? null : redesc.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Integer getUsecount() {
        return usecount;
    }

    public void setUsecount(Integer usecount) {
        this.usecount = usecount;
    }

    public String getRediskey() {
        return rediskey;
    }

    public void setRediskey(String rediskey) {
        this.rediskey = rediskey == null ? null : rediskey.trim();
    }

	@Override
	public String toString() {
		return "RedEnvelopes [id=" + id + ", userid=" + userid + ", currency=" + currency + ", amount=" + amount
				+ ", redesc=" + redesc + ", count=" + count + ", state=" + state + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", usecount=" + usecount + ", rediskey=" + rediskey + "]";
	}
}