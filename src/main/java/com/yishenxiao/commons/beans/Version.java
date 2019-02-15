package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class Version implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String versionname;

    private Integer type;

    private String versionnum;

    private Integer androidstate;

    private Integer iosstate;

    private String androidurl;

    private String iosurl;

    private Date createtime;

    private Date updatetime;

    private String versiondesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname == null ? null : versionname.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(String versionnum) {
        this.versionnum = versionnum == null ? null : versionnum.trim();
    }

    public Integer getAndroidstate() {
        return androidstate;
    }

    public void setAndroidstate(Integer androidstate) {
        this.androidstate = androidstate;
    }

    public Integer getIosstate() {
        return iosstate;
    }

    public void setIosstate(Integer iosstate) {
        this.iosstate = iosstate;
    }

    public String getAndroidurl() {
        return androidurl;
    }

    public void setAndroidurl(String androidurl) {
        this.androidurl = androidurl == null ? null : androidurl.trim();
    }

    public String getIosurl() {
        return iosurl;
    }

    public void setIosurl(String iosurl) {
        this.iosurl = iosurl == null ? null : iosurl.trim();
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

    public String getVersiondesc() {
        return versiondesc;
    }

    public void setVersiondesc(String versiondesc) {
        this.versiondesc = versiondesc == null ? null : versiondesc.trim();
    }

	@Override
	public String toString() {
		return "Version [id=" + id + ", versionname=" + versionname + ", type=" + type + ", versionnum=" + versionnum
				+ ", androidstate=" + androidstate + ", iosstate=" + iosstate + ", androidurl=" + androidurl
				+ ", iosurl=" + iosurl + ", createtime=" + createtime + ", updatetime=" + updatetime + ", versiondesc="
				+ versiondesc + "]";
	}
}