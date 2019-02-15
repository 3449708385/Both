package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;

public class NativeCode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String nativecode;

    private String nativename;

    private Date createtime;
    
    private String nativeCodeNickname;

    private Date updatetime;

    private Integer natvecodeindex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNativecode() {
        return nativecode;
    }

    public void setNativecode(String nativecode) {
        this.nativecode = nativecode == null ? null : nativecode.trim();
    }

    public String getNativename() {
        return nativename;
    }

    public void setNativename(String nativename) {
        this.nativename = nativename == null ? null : nativename.trim();
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

    public String getNativeCodeNickname() {
		return nativeCodeNickname;
	}

	public void setNativeCodeNickname(String nativeCodeNickname) {
		this.nativeCodeNickname = nativeCodeNickname;
	}

	public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getNatvecodeindex() {
        return natvecodeindex;
    }

    public void setNatvecodeindex(Integer natvecodeindex) {
        this.natvecodeindex = natvecodeindex;
    }

	@Override
	public String toString() {
		return "NativeCode [id=" + id + ", nativecode=" + nativecode + ", nativename=" + nativename + ", createtime="
				+ createtime + ", nativeCodeNickname=" + nativeCodeNickname + ", updatetime=" + updatetime
				+ ", natvecodeindex=" + natvecodeindex + "]";
	}
}