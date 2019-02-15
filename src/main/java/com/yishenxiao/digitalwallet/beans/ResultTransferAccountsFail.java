package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.util.Map;

public class ResultTransferAccountsFail implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String jsonrpc;
   private Map<String,Object> error;
   private Long id;
   
	public String getJsonrpc() {
		return jsonrpc;
	}
	
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	
	public Map<String, Object> getError() {
		return error;
	}
	
	public void setError(Map<String, Object> error) {
		this.error = error;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "ResultTransferAccountsFail [jsonrpc=" + jsonrpc + ", error=" + error + ", id=" + id + "]";
	}
   
}
