package com.yishenxiao.commons.beans.mongobean;

import java.util.Comparator;

public class VolumeAscSort implements Comparator<DigitalCurrencyQuotation> {

	@Override
	public int compare(DigitalCurrencyQuotation dcq1, DigitalCurrencyQuotation dcq2) {
		// 按照24小时成交量由高到低排序，如果相同则按照名字自然排序
		double res = (Double.parseDouble(dcq1.getVolume_usd_24h()) - Double.parseDouble(dcq2.getVolume_usd_24h()));
		if (res == 0.0) {
			return dcq1.getCoinId().compareTo(dcq2.getCoinId());
		}
		return res > 0 ? -1 : 1;
	}

}
