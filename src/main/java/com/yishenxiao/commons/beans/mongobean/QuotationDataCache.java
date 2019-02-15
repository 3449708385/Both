package com.yishenxiao.commons.beans.mongobean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class QuotationDataCache {

	// 美元汇率
	private static String exchangeRate = "";
	// 币种名称集合
	private static Map<String, String> currencyNameMap = new HashMap<String, String>();
	// 数字货币行情
	private static List<DigitalCurrencyQuotation> dcqMarketValueList = new ArrayList<DigitalCurrencyQuotation>();
	// 数字货币详细信息
	private static Map<String, CurrencyInfo> currencyInfoMap = new ConcurrentHashMap<String, CurrencyInfo>();

	/**
	 * 更新美元汇率
	 * 
	 * @param exchangeNewRate
	 */
	public static void updateRate(String exchangeNewRate) {
		exchangeRate = exchangeNewRate;
	}

	/**
	 * 更新数字货币行情数据
	 * 
	 * @param exchangeNewRate
	 * @param currencyNameNewMap
	 * @param dcqMarketValueNewList
	 */
	public static void updateData(Map<String, String> currencyNameNewMap,
			List<DigitalCurrencyQuotation> dcqMarketValueNewList, Map<String, CurrencyInfo> currencyInfoNewMap) {
		currencyNameMap = currencyNameNewMap;
		dcqMarketValueList = dcqMarketValueNewList;
		currencyInfoMap = currencyInfoNewMap;
	}

	/**
	 * 获取美元汇率
	 * 
	 * @return
	 */
	public static String getRate() {
		return exchangeRate;
	}

	/**
	 * 获取币种名称
	 * 
	 * @return
	 */
	public static Map<String, String> getCurrencyName() {
		return currencyNameMap;
	}

	/**
	 * 获取所有市值数据
	 * 
	 * @return
	 */
	public static List<DigitalCurrencyQuotation> getAllMarketValue() {
		return dcqMarketValueList;
	}

	/**
	 * 分页获取数字货币行情，按照市值由高到低排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static List<DigitalCurrencyQuotation> getDcqMarketValue(Integer pageNumber, Integer pageSize) {
		// 数字货币行情，按照市值由高到低排序
		List<DigitalCurrencyQuotation> dcqMarketValueAscList = dcqMarketValueList;
		Collections.sort(dcqMarketValueAscList, new MarketValueSort());
		List<DigitalCurrencyQuotation> marketValueList = new ArrayList<DigitalCurrencyQuotation>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		DigitalCurrencyQuotation digitalCurrencyQuotation = null;
		for (int i = 0; i < pageSize && i < dcqMarketValueAscList.size() - currIdx; i++) {
			digitalCurrencyQuotation = dcqMarketValueAscList.get(currIdx + i);
			marketValueList.add(digitalCurrencyQuotation);
		}
		return marketValueList;
	}

	/**
	 * 分页获取数字货币行情，按照涨幅由高到低排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static List<DigitalCurrencyQuotation> getDcqRiseAsc(Integer pageNumber, Integer pageSize) {
		// 数字货币行情，按照涨幅由高到低排序
		List<DigitalCurrencyQuotation> dcqRiseAscList = dcqMarketValueList;
		Collections.sort(dcqRiseAscList, new RiseAscSort());
		List<DigitalCurrencyQuotation> riseAscList = new ArrayList<DigitalCurrencyQuotation>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		DigitalCurrencyQuotation digitalCurrencyQuotation = null;
		for (int i = 0; i < pageSize && i < dcqRiseAscList.size() - currIdx; i++) {
			digitalCurrencyQuotation = dcqRiseAscList.get(currIdx + i);
			riseAscList.add(digitalCurrencyQuotation);
		}
		return riseAscList;
	}

	/**
	 * 分页获取数字货币行情，按照涨幅由低到高排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static List<DigitalCurrencyQuotation> getDcqRiseDesc(Integer pageNumber, Integer pageSize) {
		// 数字货币行情，按照涨幅由低到高排序
		List<DigitalCurrencyQuotation> dcqRiseDescList = dcqMarketValueList;
		Collections.sort(dcqRiseDescList, new RiseDescSort());
		List<DigitalCurrencyQuotation> riseDescList = new ArrayList<DigitalCurrencyQuotation>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		DigitalCurrencyQuotation digitalCurrencyQuotation = null;
		for (int i = 0; i < pageSize && i < dcqRiseDescList.size() - currIdx; i++) {
			digitalCurrencyQuotation = dcqRiseDescList.get(currIdx + i);
			riseDescList.add(digitalCurrencyQuotation);
		}
		return riseDescList;
	}

	/**
	 * 分页获取数字货币行情，按照24小时成交量由低到高排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static List<DigitalCurrencyQuotation> getDcqVolumeAsc(Integer pageNumber, Integer pageSize) {
		// 数字货币行情，按照24小时成交量由高到低排序
		List<DigitalCurrencyQuotation> dcqVolumeAscList = dcqMarketValueList;
		Collections.sort(dcqVolumeAscList, new VolumeAscSort());
		List<DigitalCurrencyQuotation> volumeAscList = new ArrayList<DigitalCurrencyQuotation>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		DigitalCurrencyQuotation digitalCurrencyQuotation = null;
		for (int i = 0; i < pageSize && i < dcqVolumeAscList.size() - currIdx; i++) {
			digitalCurrencyQuotation = dcqVolumeAscList.get(currIdx + i);
			volumeAscList.add(digitalCurrencyQuotation);
		}
		return volumeAscList;
	}

	/**
	 * 获取数字货币详细信息，分页获取交易所
	 * 
	 * @param coinId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static CurrencyInfo getCurrencyInfo(String coinId, Integer pageNumber, Integer pageSize) {
		Map<String, CurrencyInfo> currencyInfoOldMap = currencyInfoMap;
		CurrencyInfo currencyInfo = null;
		CurrencyInfo newCurrencyInfo = new CurrencyInfo();
		List<Object> dataList = new ArrayList<Object>();
		List<Object> allDataList = new ArrayList<Object>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		Object object = null;
		if (currencyInfoOldMap.containsKey(coinId)) {
			currencyInfo = currencyInfoOldMap.get(coinId);
			allDataList = currencyInfo.getExchanges();
			if (allDataList != null) {
				if (!allDataList.isEmpty()) {
					for (int i = 0; i < pageSize && i < allDataList.size() - currIdx; i++) {
						object = allDataList.get(currIdx + i);
						dataList.add(object);
					}
				}
			}
		}
		if (currencyInfo != null && !dataList.isEmpty()) {
			newCurrencyInfo.setCoinId(currencyInfo.getCoinId());
			newCurrencyInfo.setDesc(currencyInfo.getDesc());
			newCurrencyInfo.setEnName(currencyInfo.getEnName());
			newCurrencyInfo.setZhName(currencyInfo.getZhName());
			newCurrencyInfo.setExchangeNumber(currencyInfo.getExchangeNumber());
			newCurrencyInfo.setPublishTime(currencyInfo.getPublishTime());
			newCurrencyInfo.setWhitePaper(currencyInfo.getWhitePaper());
			newCurrencyInfo.setWebsite(currencyInfo.getWebsite());
			newCurrencyInfo.setBlockStation(currencyInfo.getBlockStation());
			newCurrencyInfo.setExchanges(dataList);
			newCurrencyInfo.setDigitalCurrencyQuotation(currencyInfo.getDigitalCurrencyQuotation());
		}
		if (newCurrencyInfo != null) {
			if (StringUtils.isNotEmpty(newCurrencyInfo.getCoinId())) {
				return newCurrencyInfo;
			}
		}
		return null;
	}

}
