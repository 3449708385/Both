package com.yishenxiao.commons.service.impl.quzrtz;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yishenxiao.commons.beans.mongobean.QuotationDataCache;
import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;

public class HandleExchangeRateDataServiceImpl extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(HandleExchangeRateDataServiceImpl.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// 更新汇率
		QuotationDataCache.updateRate(getRate());
	}

	/**
	 * 获取美元汇率
	 * 
	 * @return
	 */
	private String getRate() {
		String exchangeRate = "";
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("symbols", PropertiesUtils.getInfoConfigProperties().getProperty("exchangeRate.symbols"));
		String resData = HttpClientUtils.getSendRequest(
				PropertiesUtils.getInfoConfigProperties().getProperty("exchangeRate.dataPath"), bodyMap);
		if (StringUtils.isNotEmpty(resData)) {
			try {
				JSONObject jsonData = new JSONObject(resData);
				if (jsonData != null) {
					if (jsonData.has("rates")) {
						JSONObject jsonObject = jsonData.getJSONObject("rates");
						if (jsonObject != null) {
							if (jsonObject.has("CNY") && jsonObject.has("USD")) {
								BigDecimal cny = new BigDecimal(jsonObject.getString("CNY"));
								BigDecimal usd = new BigDecimal(jsonObject.getString("USD"));
								exchangeRate = cny.divide(usd, 4, BigDecimal.ROUND_HALF_UP).toString();
							}
						}
					}
				}
			} catch (JSONException e) {
				logger.error("获取美元汇率定时器json转换失败！");
			}
		}
		return exchangeRate;
	}

}
