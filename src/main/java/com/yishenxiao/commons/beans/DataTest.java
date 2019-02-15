package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class DataTest implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String createtime;

    private String attrstatus;

    private String city;

    private String sex;

    private String province;

    private String snsflag;

    private String username;

    private String displayname;

    private String nickname;

    private String membercount;

    private String remarkname;

    private String keyword;

    private String owner;

    private String puid;

    private String type;

    private String q;

    private String r;

    private String s;

    private String t;

    private String u;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getAttrstatus() {
        return attrstatus;
    }

    public void setAttrstatus(String attrstatus) {
        this.attrstatus = attrstatus == null ? null : attrstatus.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getSnsflag() {
        return snsflag;
    }

    public void setSnsflag(String snsflag) {
        this.snsflag = snsflag == null ? null : snsflag.trim();
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

    public String getMembercount() {
        return membercount;
    }

    public void setMembercount(String membercount) {
        this.membercount = membercount == null ? null : membercount.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q == null ? null : q.trim();
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r == null ? null : r.trim();
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s == null ? null : s.trim();
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t == null ? null : t.trim();
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u == null ? null : u.trim();
    }

	@Override
	public String toString() {
		return "DataTest [id=" + id + ", createtime=" + createtime + ", attrstatus=" + attrstatus + ", city=" + city
				+ ", sex=" + sex + ", province=" + province + ", snsflag=" + snsflag + ", username=" + username
				+ ", displayname=" + displayname + ", nickname=" + nickname + ", membercount=" + membercount
				+ ", remarkname=" + remarkname + ", keyword=" + keyword + ", owner=" + owner + ", puid=" + puid
				+ ", type=" + type + ", q=" + q + ", r=" + r + ", s=" + s + ", t=" + t + ", u=" + u + "]";
	}
}