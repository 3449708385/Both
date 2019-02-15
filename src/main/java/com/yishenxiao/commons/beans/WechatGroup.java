package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class WechatGroup implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date createtime;

    private Integer attrstatus;

    private String city;

    private Integer sex;

    private String province;

    private Integer snsflag;

    private String username;

    private String displayname;

    private String nickname;

    private Integer membercount;

    private String remarkname;

    private String keyword;

    private String owner;

    private String puid;

    private String nicknamemd5;

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

    public Integer getAttrstatus() {
        return attrstatus;
    }

    public void setAttrstatus(Integer attrstatus) {
        this.attrstatus = attrstatus;
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

    public Integer getMembercount() {
        return membercount;
    }

    public void setMembercount(Integer membercount) {
        this.membercount = membercount;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getNicknamemd5() {
        return nicknamemd5;
    }

    public void setNicknamemd5(String nicknamemd5) {
        this.nicknamemd5 = nicknamemd5 == null ? null : nicknamemd5.trim();
    }

	@Override
	public String toString() {
		return "WechatGroup [id=" + id + ", createtime=" + createtime + ", attrstatus=" + attrstatus + ", city=" + city
				+ ", sex=" + sex + ", province=" + province + ", snsflag=" + snsflag + ", username=" + username
				+ ", displayname=" + displayname + ", nickname=" + nickname + ", membercount=" + membercount
				+ ", remarkname=" + remarkname + ", keyword=" + keyword + ", owner=" + owner + ", puid=" + puid
				+ ", nicknamemd5=" + nicknamemd5 + "]";
	}
}