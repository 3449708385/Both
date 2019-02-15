package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;

public class ResultTransferAccountsSuc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonrpc;
	private String result;
	private Long id;
	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ResultTransferAccountsSuc [jsonrpc=" + jsonrpc + ", result=" + result + ", id=" + id + "]";
	}
}
