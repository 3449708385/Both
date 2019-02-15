package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class WechatFriend implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date createtime;

    private String city;

    private Integer sex;

    private Integer uin;

    private String province;

    private Integer snsflag;

    private String username;

    private String displayname;

    private String nickname;

    private String remarkname;

    private String keyword;

    private String puid;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getUin() {
        return uin;
    }

    public void setUin(Integer uin) {
        this.uin = uin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getSnsflag() {
        return snsflag;
    }

    public void setSnsflag(Integer snsflag) {
        this.snsflag = snsflag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname == null ? null : displayname.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname == null ? null : remarkname.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

	@Override
	public String toString() {
		return "WechatFriend [id=" + id + ", createtime=" + createtime + ", city=" + city + ", sex=" + sex + ", uin="
				+ uin + ", province=" + province + ", snsflag=" + snsflag + ", username=" + username + ", displayname="
				+ displayname + ", nickname=" + nickname + ", remarkname=" + remarkname + ", keyword=" + keyword
				+ ", puid=" + puid + "]";
	}
}