package com.yishenxiao.commons.beans.mongobean;

import java.util.Comparator;

public class MarketValueSort implements Comparator<DigitalCurrencyQuotation> {

	@Override
	public int compare(DigitalCurrencyQuotation dcq1, DigitalCurrencyQuotation dcq2) {
		// 按照市值由高到低排序，如果相同则按照名字自然排序
		double res = (Double.parseDouble(dcq1.getMarket_cap_usd()) - Double.parseDouble(dcq2.getMarket_cap_usd()));
		if (res == 0.0) {
			return dcq1.getCoinId().compareTo(dcq2.getCoinId());
		}
		return res > 0 ? -1 : 1;
	}

}
