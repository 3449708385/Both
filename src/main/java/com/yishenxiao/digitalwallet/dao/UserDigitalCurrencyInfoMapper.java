package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;

public interface UserDigitalCurrencyInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDigitalCurrencyInfo record);

    int insertSelective(UserDigitalCurrencyInfo record);

    UserDigitalCurrencyInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDigitalCurrencyInfo record);

    int updateByPrimaryKey(UserDigitalCurrencyInfo record);

	List<UserDigitalCurrencyInfo> queryUserDigitalCurrencyInfoData(Map<String, Object> map);

	int updateDigitalcurrencybalance(Map<String, Object> map);

	UserDigitalCurrencyInfo queryByDigitalCurrencyId(Map<String, Object> map);

	int updateByDigitalCurrency(Map<String, Object> map);

	int updatUserDigitalCurrencyData(Map<String, Object> map);

	List<UserDigitalCurrencyInfo> queryByDigitalCurrencyIdList(Map<String, Object> map);

	int updateData(Map<String, Object> map);

	List<UserDigitalCurrencyInfo> queryByDigitalCurrency(Map<String, Object> map);

	int insertList(List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList);

	int updateUserData(Map<String, Object> map);

	int updateForeignUserData(Map<String, Object> map);

	int updateByDigitalBalance(Map<String, Object> map);

	int deleteByuserId(Map<String, Object> map);
}