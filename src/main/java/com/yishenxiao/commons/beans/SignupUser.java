package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SignupUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> map = new HashMap<String,Object>();
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "SignupUser [map=" + map + "]";
	}
}
