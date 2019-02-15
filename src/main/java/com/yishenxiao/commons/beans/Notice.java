package com.yishenxiao.commons.beans;

import java.util.Date;

public class Notice {
    private Long id;

    private String noticename;

    private Integer type;

    private String noticenum;

    private Integer androidstate;

    private Integer iosstate;

    private String androidurl;

    private String iosurl;

    private Date createtime;

    private Date updatetime;

    private String noticedesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticename() {
        return noticename;
    }

    public void setNoticename(String noticename) {
        this.noticename = noticename == null ? null : noticename.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNoticenum() {
        return noticenum;
    }

    public void setNoticenum(String noticenum) {
        this.noticenum = noticenum == null ? null : noticenum.trim();
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

    public String getNoticedesc() {
        return noticedesc;
    }

    public void setNoticedesc(String noticedesc) {
        this.noticedesc = noticedesc == null ? null : noticedesc.trim();
    }
}