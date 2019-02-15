package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;

public class WalletData implements Serializable {

	private static final long serialVersionUID = 1L;
	// 数字货币市场价格信息id
	private Long id;
	// 数字货币全称
	private String coinName;
	// 数字货币名称
	private String name;
	// 数字货币图片地址
	private String imageUrl;
	// 余额
	private String balance;
	// 手续费
	private String fee;
	// 二维码地址
	private String filePath;
	// 账户地址
	private String moneyAddr;
	// 市场价格
	private String marketPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMoneyAddr() {
		return moneyAddr;
	}

	public void setMoneyAddr(String moneyAddr) {
		this.moneyAddr = moneyAddr;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Override
	public String toString() {
		return "WalletData [id=" + id + ", coinName=" + coinName + ", name=" + name + ", imageUrl=" + imageUrl
				+ ", balance=" + balance + ", fee=" + fee + ", filePath=" + filePath + ", moneyAddr=" + moneyAddr
				+ ", marketPrice=" + marketPrice + "]";
	}

}
