package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserSchedule implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String nickname;

    private Long userid;

    private Date createtime;

    private String purseaddress;

    private String headicon;

    private String paymentpw;

    private BigDecimal monetary;

    private Date updatetime;

    private Integer paypwone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
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

    public String getPurseaddress() {
        return purseaddress;
    }

    public void setPurseaddress(String purseaddress) {
        this.purseaddress = purseaddress == null ? null : purseaddress.trim();
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon == null ? null : headicon.trim();
    }

    public String getPaymentpw() {
        return paymentpw;
    }

    public void setPaymentpw(String paymentpw) {
        this.paymentpw = paymentpw == null ? null : paymentpw.trim();
    }

    public BigDecimal getMonetary() {
        return monetary;
    }

    public void setMonetary(BigDecimal monetary) {
        this.monetary = monetary;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getPaypwone() {
        return paypwone;
    }

    public void setPaypwone(Integer paypwone) {
        this.paypwone = paypwone;
    }

	@Override
	public String toString() {
		return "UserSchedule [id=" + id + ", nickname=" + nickname + ", userid=" + userid + ", createtime=" + createtime
				+ ", purseaddress=" + purseaddress + ", headicon=" + headicon + ", paymentpw=" + paymentpw
				+ ", monetary=" + monetary + ", updatetime=" + updatetime + ", paypwone=" + paypwone + "]";
	}
}