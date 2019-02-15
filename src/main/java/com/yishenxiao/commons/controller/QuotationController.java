package com.yishenxiao.commons.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.mongobean.CurrencyInfo;
import com.yishenxiao.commons.beans.mongobean.QuotationDataCache;
import com.yishenxiao.commons.beans.postbean.CurrencyInfoBean;
import com.yishenxiao.commons.beans.postbean.CurrencyNamesBean;
import com.yishenxiao.commons.beans.postbean.ExchangeRateBean;
import com.yishenxiao.commons.beans.postbean.MarketValueBean;
import com.yishenxiao.commons.beans.postbean.RiseValueBean;
import com.yishenxiao.commons.beans.postbean.VolumeValueBean;
import com.yishenxiao.commons.utils.ReturnInfo;

@Controller
@RequestMapping("quotation")
public class QuotationController {

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "marketValue", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo marketValue(@RequestBody MarketValueBean marketValueBean) {
		String pageNumber = marketValueBean.getPageNumber();
		if (StringUtils.isEmpty(pageNumber)) {
			return ReturnInfo.error("参数错误！");
		}
		String pageSize = marketValueBean.getPageSize();
		if (StringUtils.isEmpty(pageSize)) {
			return ReturnInfo.error("参数错误！");
		}
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.put("data",
				QuotationDataCache.getDcqMarketValue(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
		returnInfo.put("total", QuotationDataCache.getCurrencyName().size());
		return returnInfo;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "riseValue", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo riseValue(@RequestBody RiseValueBean riseValueBean) {
		String pageNumber = riseValueBean.getPageNumber();
		if (StringUtils.isEmpty(pageNumber)) {
			return ReturnInfo.error("参数错误！");
		}
		String pageSize = riseValueBean.getPageSize();
		if (StringUtils.isEmpty(pageSize)) {
			return ReturnInfo.error("参数错误！");
		}
		String sortType = riseValueBean.getSortType();
		if (StringUtils.isEmpty(sortType)) {
			return ReturnInfo.error("参数错误！");
		}
		ReturnInfo returnInfo = new ReturnInfo();
		if ("1".equals(sortType)) {
			returnInfo.put("data",
					QuotationDataCache.getDcqRiseAsc(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
		} else {
			returnInfo.put("data",
					QuotationDataCache.getDcqRiseDesc(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
		}
		returnInfo.put("total", QuotationDataCache.getCurrencyName().size());
		return returnInfo;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "volumeValue", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo volumeValue(@RequestBody VolumeValueBean volumeValueBean) {
		String pageNumber = volumeValueBean.getPageNumber();
		if (StringUtils.isEmpty(pageNumber)) {
			return ReturnInfo.error("参数错误！");
		}
		String pageSize = volumeValueBean.getPageSize();
		if (StringUtils.isEmpty(pageSize)) {
			return ReturnInfo.error("参数错误！");
		}
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.put("data",
				QuotationDataCache.getDcqVolumeAsc(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
		returnInfo.put("total", QuotationDataCache.getCurrencyName().size());
		return returnInfo;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "currencyNames", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo currencyNames(@RequestBody CurrencyNamesBean currencyNamesBean) {
		return ReturnInfo.info(200, QuotationDataCache.getCurrencyName());
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "exchangeRate", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo exchangeRate(@RequestBody ExchangeRateBean exchangeRateBean) {
		return ReturnInfo.info(200, QuotationDataCache.getRate());
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "currencyInfo", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo currencyInfo(@RequestBody CurrencyInfoBean currencyInfoBean) {
		String coinId = currencyInfoBean.getCoinId();
		if (StringUtils.isEmpty(coinId)) {
			ReturnInfo.error("参数错误！");
		}
		String pageNumber = currencyInfoBean.getPageNumber();
		if (StringUtils.isEmpty(pageNumber)) {
			return ReturnInfo.error("参数错误！");
		}
		String pageSize = currencyInfoBean.getPageSize();
		if (StringUtils.isEmpty(pageSize)) {
			return ReturnInfo.error("参数错误！");
		}
		CurrencyInfo currencyInfo = QuotationDataCache.getCurrencyInfo(coinId, Integer.parseInt(pageNumber),
				Integer.parseInt(pageSize));
		if (currencyInfo != null) {
			if (!currencyInfo.getExchanges().isEmpty()) {
				return ReturnInfo.info(200, currencyInfo);
			}
		}
		return ReturnInfo.error(500, "未查询到货币的详细信息");
	}

}
