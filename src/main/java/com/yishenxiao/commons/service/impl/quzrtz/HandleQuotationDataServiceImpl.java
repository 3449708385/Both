package com.yishenxiao.commons.service.impl.quzrtz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.yishenxiao.commons.beans.mongobean.CurrencyInfo;
import com.yishenxiao.commons.beans.mongobean.DigitalCurrencyQuotation;
import com.yishenxiao.commons.beans.mongobean.QuotationDataCache;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;

public class HandleQuotationDataServiceImpl extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(HandleQuotationDataServiceImpl.class);

	private MongoTemplate mongoTemplate1 = (MongoTemplate) SpringContextUtils.getBean("mongoTemplate1");

	private MongoTemplate mongoTemplate2 = (MongoTemplate) SpringContextUtils.getBean("mongoTemplate2");

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// 获取mongoDB连接
		DBCollection dbCol = mongoTemplate2
				.getCollection(PropertiesUtils.getInfoConfigProperties().getProperty("mongoDB.coll"));
		// 访问接口获取行情数据
		String resData = HttpClientUtils
				.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("quotation.dataPath"), null);
		if (StringUtils.isNotEmpty(resData)) {
			try {
				JSONArray jsonData = new JSONArray(resData);
				// 创建存储集合
				Map<String, String> currencyNameNewMap = new HashMap<String, String>();
				List<DigitalCurrencyQuotation> dcqMarketValueNewList = new ArrayList<DigitalCurrencyQuotation>();
				Map<String, CurrencyInfo> currencyInfoNewMap = new ConcurrentHashMap<String, CurrencyInfo>();
				String volume_usd_24h = "";
				String market_cap_usd = "";
				String percent_change_24h = "";
				JSONObject jsonObject = null;
				DigitalCurrencyQuotation digitalCurrencyQuotation = null;
				CurrencyInfo currencyInfo = null;
				for (int i = 0; i < jsonData.length(); i++) {
					jsonObject = jsonData.getJSONObject(i);
					digitalCurrencyQuotation = new DigitalCurrencyQuotation();
					digitalCurrencyQuotation.setCoinId(jsonObject.getString("id"));
					digitalCurrencyQuotation.setCoinName(jsonObject.getString("name"));
					digitalCurrencyQuotation.setSymbol(jsonObject.getString("symbol"));
					digitalCurrencyQuotation.setRank(jsonObject.getString("rank"));
					digitalCurrencyQuotation.setPrice_usd(jsonObject.getString("price_usd"));
					digitalCurrencyQuotation.setPrice_btc(jsonObject.getString("price_btc"));
					// 因为需要根据24小时成交量排序，所以不能为空，为空的设置为0
					volume_usd_24h = jsonObject.getString("24h_volume_usd");
					if ("null".equals(volume_usd_24h)) {
						volume_usd_24h = "0";
					}
					digitalCurrencyQuotation.setVolume_usd_24h(volume_usd_24h);
					// 因为需要根据总市值排序，所以不能为空，为空的设置为0
					market_cap_usd = jsonObject.getString("market_cap_usd");
					if ("null".equals(market_cap_usd)) {
						market_cap_usd = "0";
					}
					digitalCurrencyQuotation.setMarket_cap_usd(market_cap_usd);
					digitalCurrencyQuotation.setAvailable_supply(jsonObject.getString("available_supply"));
					digitalCurrencyQuotation.setTotal_supply(jsonObject.getString("total_supply"));
					digitalCurrencyQuotation.setMax_supply(jsonObject.getString("max_supply"));
					digitalCurrencyQuotation.setPercent_change_1h(jsonObject.getString("percent_change_1h"));
					// 因为需要根据涨幅排序，所以不能为空，为空的设置为0，前端设置为无数据
					percent_change_24h = jsonObject.getString("percent_change_24h");
					if ("null".equals(percent_change_24h)) {
						percent_change_24h = "0";
					}
					digitalCurrencyQuotation.setPercent_change_24h(percent_change_24h);
					digitalCurrencyQuotation.setPercent_change_7d(jsonObject.getString("percent_change_7d"));
					digitalCurrencyQuotation.setLast_updated(jsonObject.getString("last_updated"));
					currencyNameNewMap.put(jsonObject.getString("symbol"), jsonObject.getString("id"));
					dcqMarketValueNewList.add(digitalCurrencyQuotation);
					currencyInfo = getCurrencyInfo(dbCol, jsonObject.getString("id"), digitalCurrencyQuotation);
					if (currencyInfo == null) {
						currencyInfo = new CurrencyInfo();
					}
					currencyInfoNewMap.put(jsonObject.getString("id"), currencyInfo);
				}
				// 更新数据
				QuotationDataCache.updateData(currencyNameNewMap, dcqMarketValueNewList, currencyInfoNewMap);
			} catch (JSONException e) {
				logger.error("获取行情定时器json转换失败！");
			}
		}

	}

	private CurrencyInfo getCurrencyInfo(DBCollection dbCol, String coinId,
			DigitalCurrencyQuotation digitalCurrencyQuotation) {
		BasicDBObject query = new BasicDBObject();
		query.put("name", coinId);
		DBCursor ret = dbCol.find(query);
		CurrencyInfo currencyInfo = null;
		if (ret.hasNext()) {
			BasicDBObject bdbObj = (BasicDBObject) ret.next();
			if (bdbObj != null) {
				currencyInfo = new CurrencyInfo();
				currencyInfo.setCoinId(bdbObj.getString("name"));
				currencyInfo.setDesc(handleDesc(bdbObj.getString("介绍").split(",")));
				currencyInfo.setEnName(bdbObj.getString("英文名"));
				currencyInfo.setZhName(bdbObj.getString("中文名"));
				currencyInfo.setExchangeNumber(bdbObj.getString("上架交易所"));
				currencyInfo.setPublishTime(bdbObj.getString("发行时间"));
				currencyInfo.setWhitePaper(bdbObj.getString("白皮书"));
				currencyInfo.setWebsite(handleData(bdbObj.getString("网站").split(",")));
				currencyInfo.setBlockStation(handleData(bdbObj.getString("区块站").split(",")));
				currencyInfo.setExchanges(getExchangeInfo(coinId));
				currencyInfo.setDigitalCurrencyQuotation(digitalCurrencyQuotation);
			}
		}
		return currencyInfo;
	}

	@SuppressWarnings("unchecked")
	private List<Object> getExchangeInfo(String coinId) {
		List<Object> allExchangeList = new ArrayList<Object>();
		// 连接mongoDB，获取交易所数据
		DBCollection dbCol = mongoTemplate1.getCollection(coinId);
		// 按照时间倒序排序获取最新时间的数据，一条数据
		DBCursor dbCur = dbCol.find().limit(1).sort(new BasicDBObject("_id", -1));
		BasicDBObject bdbObj = null;
		if (dbCur.hasNext()) {
			bdbObj = (BasicDBObject) dbCur.next();
		}
		if (bdbObj != null) {
			// 获取所有交易所数据
			allExchangeList = (List<Object>) bdbObj.toMap().get("pair_list");
		}
		return allExchangeList;
	}

	private String handleDesc(String[] strs) {
		String desc = "";
		for (String str : strs) {
			str = str.replace("\"", "");
			desc += str;
		}
		desc = desc.replaceAll("\\s*", "");
		return desc.substring(1, desc.length() - 1);
	}

	private List<String> handleData(String[] strs) {
		List<String> res = new ArrayList<String>();
		for (String str : strs) {
			str = str.replace("\"", "").replace("[", "").replace("]", "").replaceAll("\\s*", "");
			res.add(str);
		}
		return res;
	}

}
