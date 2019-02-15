package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class PhoneBlacklist implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

	@Override
	public String toString() {
		return "PhoneBlacklist [id=" + id + ", userid=" + userid + ", phone=" + phone + "]";
	}
}