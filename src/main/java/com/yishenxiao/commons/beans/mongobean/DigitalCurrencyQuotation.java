package com.yishenxiao.commons.beans.mongobean;

public class DigitalCurrencyQuotation {

	private String coinId;

	private String coinName;

	private String symbol;
	// coin市值排名
	private String rank;
	// 对美元的价格
	private String price_usd;
	// 对比特币的价格
	private String price_btc;
	// 24小时总交易量
	private String volume_usd_24h;
	// 总市值
	private String market_cap_usd;
	// 可流通量
	private String available_supply;
	// 总流通量
	private String total_supply;
	// 发行总量
	private String max_supply;
	// 前一小时涨跌幅
	private String percent_change_1h;
	// 前24小时涨跌幅
	private String percent_change_24h;
	// 前7天涨跌幅
	private String percent_change_7d;
	// 更新时间
	private String last_updated;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(String price_usd) {
		this.price_usd = price_usd;
	}

	public String getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(String price_btc) {
		this.price_btc = price_btc;
	}

	public String getVolume_usd_24h() {
		return volume_usd_24h;
	}

	public void setVolume_usd_24h(String volume_usd_24h) {
		this.volume_usd_24h = volume_usd_24h;
	}

	public String getMarket_cap_usd() {
		return market_cap_usd;
	}

	public void setMarket_cap_usd(String market_cap_usd) {
		this.market_cap_usd = market_cap_usd;
	}

	public String getAvailable_supply() {
		return available_supply;
	}

	public void setAvailable_supply(String available_supply) {
		this.available_supply = available_supply;
	}

	public String getTotal_supply() {
		return total_supply;
	}

	public void setTotal_supply(String total_supply) {
		this.total_supply = total_supply;
	}

	public String getMax_supply() {
		return max_supply;
	}

	public void setMax_supply(String max_supply) {
		this.max_supply = max_supply;
	}

	public String getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(String percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public String getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(String percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public String getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(String percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	@Override
	public String toString() {
		return "DigitalCurrencyQuotation [coinId=" + coinId + ", coinName=" + coinName + ", symbol=" + symbol
				+ ", rank=" + rank + ", price_usd=" + price_usd + ", price_btc=" + price_btc + ", volume_usd_24h="
				+ volume_usd_24h + ", market_cap_usd=" + market_cap_usd + ", available_supply=" + available_supply
				+ ", total_supply=" + total_supply + ", max_supply=" + max_supply + ", percent_change_1h="
				+ percent_change_1h + ", percent_change_24h=" + percent_change_24h + ", percent_change_7d="
				+ percent_change_7d + ", last_updated=" + last_updated + "]";
	}

}
