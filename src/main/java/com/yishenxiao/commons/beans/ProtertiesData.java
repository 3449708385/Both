package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class ProtertiesData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String datakey;

    private String datavalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatakey() {
        return datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey == null ? null : datakey.trim();
    }

    public String getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(String datavalue) {
        this.datavalue = datavalue == null ? null : datavalue.trim();
    }

	@Override
	public String toString() {
		return "ProtertiesData [id=" + id + ", datakey=" + datakey + ", datavalue=" + datavalue + "]";
	}
}