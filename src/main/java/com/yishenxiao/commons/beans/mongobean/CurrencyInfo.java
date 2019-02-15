package com.yishenxiao.commons.beans.mongobean;

import java.util.List;

public class CurrencyInfo {

	// 币id
	private String coinId;
	// 描述
	private String desc;
	// 英文名
	private String enName;
	// 中文名
	private String zhName;
	// 上架交易所数量
	private String exchangeNumber;
	// 发行时间
	private String publishTime;
	// 白皮书
	private String whitePaper;
	// 网站
	private List<String> website;
	// 区块站
	private List<String> blockStation;
	// 上架交易所
	private List<Object> exchanges;
	// 市值信息
	private DigitalCurrencyQuotation digitalCurrencyQuotation;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getWhitePaper() {
		return whitePaper;
	}

	public void setWhitePaper(String whitePaper) {
		this.whitePaper = whitePaper;
	}

	public List<String> getWebsite() {
		return website;
	}

	public void setWebsite(List<String> website) {
		this.website = website;
	}

	public List<String> getBlockStation() {
		return blockStation;
	}

	public void setBlockStation(List<String> blockStation) {
		this.blockStation = blockStation;
	}

	public List<Object> getExchanges() {
		return exchanges;
	}

	public void setExchanges(List<Object> exchanges) {
		this.exchanges = exchanges;
	}

	public DigitalCurrencyQuotation getDigitalCurrencyQuotation() {
		return digitalCurrencyQuotation;
	}

	public void setDigitalCurrencyQuotation(DigitalCurrencyQuotation digitalCurrencyQuotation) {
		this.digitalCurrencyQuotation = digitalCurrencyQuotation;
	}

	@Override
	public String toString() {
		return "CurrencyInfo [coinId=" + coinId + ", desc=" + desc + ", enName=" + enName + ", zhName=" + zhName
				+ ", exchangeNumber=" + exchangeNumber + ", publishTime=" + publishTime + ", whitePaper=" + whitePaper
				+ ", website=" + website + ", blockStation=" + blockStation + ", exchanges=" + exchanges
				+ ", digitalCurrencyQuotation=" + digitalCurrencyQuotation + "]";
	}

}
