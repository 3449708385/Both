package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurseBalanceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String CoinName;
	private String Name;
	private String ImageUrl;
	private String Balance;
	private String fee;
	private String filePath;
    private String moneyAddr;
    private String MarketPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCoinName() {
		return CoinName;
	}
	public void setCoinName(String coinName) {
		CoinName = coinName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
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
		return MarketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		MarketPrice = marketPrice;
	}
	@Override
	public String toString() {
		return "PurseBalanceBean [id=" + id + ", CoinName=" + CoinName + ", Name=" + Name + ", ImageUrl=" + ImageUrl
				+ ", Balance=" + Balance + ", fee=" + fee + ", filePath=" + filePath + ", moneyAddr=" + moneyAddr
				+ ", MarketPrice=" + MarketPrice + "]";
	}  
}
