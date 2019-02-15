package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String account;

    private Date createtime;

    private String username;

    private String passwd;

    private String email;

    private String phone;

    private Integer type;

    private Date updatetime;

    private Integer islogin;

    private Integer state;

    private Long parentextensionid;

    private String usertoken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getIslogin() {
        return islogin;
    }

    public void setIslogin(Integer islogin) {
        this.islogin = islogin;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getParentextensionid() {
        return parentextensionid;
    }

    public void setParentextensionid(Long parentextensionid) {
        this.parentextensionid = parentextensionid;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken == null ? null : usertoken.trim();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", createtime=" + createtime + ", username=" + username
				+ ", passwd=" + passwd + ", email=" + email + ", phone=" + phone + ", type=" + type + ", updatetime="
				+ updatetime + ", islogin=" + islogin + ", state=" + state + ", parentextensionid=" + parentextensionid
				+ ", usertoken=" + usertoken + "]";
	}
}