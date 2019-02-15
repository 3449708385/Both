package com.yishenxiao.commons.beans;

import java.util.Date;

public class FileUpload {
    private Long id;

    private Long userid;

    private Integer type;

    private String uuid;

    private String sharesecret;

    private String smalltpath;

    private Integer state;

    private Date createtime;

    private Date updatetime;

    private Long infoid;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getSharesecret() {
        return sharesecret;
    }

    public void setSharesecret(String sharesecret) {
        this.sharesecret = sharesecret == null ? null : sharesecret.trim();
    }

    public String getSmalltpath() {
        return smalltpath;
    }

    public void setSmalltpath(String smalltpath) {
        this.smalltpath = smalltpath == null ? null : smalltpath.trim();
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

    public Long getInfoid() {
        return infoid;
    }

    public void setInfoid(Long infoid) {
        this.infoid = infoid;
    }
}