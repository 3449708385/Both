package com.yishenxiao.digitalwallet.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.postbean.AddDigitalCurrencyInfoBean;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;

@Controller
@RequestMapping("digitalCurrency")
public class DigitalCurrencyInfoController {
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	/**
	 * @info 增加币种数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="addDigitalCurrencyInfo", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo addDigitalCurrencyInfo(@RequestBody AddDigitalCurrencyInfoBean addDigitalCurrencyInfoBean){
		String digitalCurrencyName= addDigitalCurrencyInfoBean.getDigitalCurrencyName();
		if(StringUtils.judgeBlank(digitalCurrencyName)){
			return ReturnInfo.error("参数错误！");
		}
		List<DigitalCurrencyInfo> digitalCurrencyInfoList= digitalCurrencyInfoService.queryByDigitalcurrencynameForList(digitalCurrencyName);
		if(digitalCurrencyInfoList.size()>0){
			return ReturnInfo.error("币名重复！");
		}
		digitalCurrencyInfoService.insertDigitalCurrencyData(digitalCurrencyName);
		return ReturnInfo.ok();
	}
	
}
