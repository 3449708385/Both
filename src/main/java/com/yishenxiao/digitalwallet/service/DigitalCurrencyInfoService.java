package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;

public interface DigitalCurrencyInfoService {

	DigitalCurrencyInfo queryById(Long digitalcurrencyid);

	DigitalCurrencyInfo queryByDigitalcurrencyname(String currency);

	int insertDigitalCurrencyData(String digitalCurrencyName);

	List<DigitalCurrencyInfo> queryByDigitalcurrencynameForList(String digitalCurrencyName);

	List<DigitalCurrencyInfo> queryByCurrencyType(String string, int i);

	List<DigitalCurrencyInfo> queryByDigitalCurrencyAddr(String token);

	List<DigitalCurrencyInfo> queryByData();

	DigitalCurrencyInfo queryByDigitalCurrencyId(Long digitalcurrencyinfoid);

}
