package com.yishenxiao.commons.beans;

import java.util.Date;

public class ShieldAccount {
    private Long id;

    private String phone;

    private Date createtime;

    private Date updatetime;

    private Long fromuser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public Long getFromuser() {
        return fromuser;
    }

    public void setFromuser(Long fromuser) {
        this.fromuser = fromuser;
    }

	@Override
	public String toString() {
		return "ShieldAccount [id=" + id + ", phone=" + phone + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", fromuser=" + fromuser + "]";
	}
}