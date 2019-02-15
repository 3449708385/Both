package com.yishenxiao.digitalwallet.dao;


import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;

public interface DigitalCurrencyInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DigitalCurrencyInfo record);

    int insertSelective(DigitalCurrencyInfo record);

    DigitalCurrencyInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DigitalCurrencyInfo record);

    int updateByPrimaryKey(DigitalCurrencyInfo record);

	DigitalCurrencyInfo queryByDigitalcurrencyname(Map<String, Object> map);

	List<DigitalCurrencyInfo> queryByDigitalcurrencynameForList(Map<String, Object> map);

	List<DigitalCurrencyInfo> queryByCurrencyType(Map<String, Object> map);

	List<DigitalCurrencyInfo> queryByDigitalCurrencyAddr(Map<String, Object> map);

	List<DigitalCurrencyInfo> queryByData();

	DigitalCurrencyInfo queryByDigitalCurrencyId(Map<String, Object> map);
}