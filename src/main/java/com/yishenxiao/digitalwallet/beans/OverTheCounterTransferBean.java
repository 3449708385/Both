package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;

public class OverTheCounterTransferBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coin;
	private String network;
	private String from;
	private String to;
	private String index;
	private String value;
	private String change_address;
	private String gas;
	private String gasPrice;
    private String token_identifier;
    private String token_address;
    private String decimals;
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChange_address() {
		return change_address;
	}
	public void setChange_address(String change_address) {
		this.change_address = change_address;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getToken_identifier() {
		return token_identifier;
	}
	public void setToken_identifier(String token_identifier) {
		this.token_identifier = token_identifier;
	}
	public String getToken_address() {
		return token_address;
	}
	public void setToken_address(String token_address) {
		this.token_address = token_address;
	}
	public String getDecimals() {
		return decimals;
	}
	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "OverTheCounterTransferBean [coin=" + coin + ", network=" + network + ", from=" + from + ", to=" + to
				+ ", index=" + index + ", value=" + value + ", change_address=" + change_address + ", gas=" + gas
				+ ", gasPrice=" + gasPrice + ", token_identifier=" + token_identifier + ", token_address="
				+ token_address + ", decimals=" + decimals + "]";
	}

}
