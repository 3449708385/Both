package com.yishenxiao.digitalwallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;

public interface UserDigitalCurrencyInfoService {

	List<UserDigitalCurrencyInfo> queryUserDigitalCurrencyInfoData(Long userId);

	int updateDigitalcurrencybalance(Long userId, Long digitalcurrencyid, Double amount);

	UserDigitalCurrencyInfo queryByDigitalCurrencyId(Long digitalCurrencyId, Long userId);

	int updateByDigitalCurrency(Long digitalCurrencyId, Long userId, Double amount, Integer type);

	int updatUserDigitalCurrencyData(Long userId, BigDecimal monery, Long digitalcurrencyId);

	List<UserDigitalCurrencyInfo> queryByDigitalCurrencyIdList(Long digitalCurrencyId, Long userId);

	int insertUserDigitalCurrencyData(UserDigitalCurrencyInfo userDigitalCurrencyInfo);

	int updateData(Long userId, Long digitalCurrencyId, BigDecimal amount);

	int insertList(List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList);

	int updateUserData(Long userid, Long digitalcurrencyinfoid, Double foreignAccount, Double borrowFrom);

	int updateForeignUserData(Long userId, Long id, Double balance);

	int updateByDigitalBalance(Long id, Long id2, Double tempMonetary);

	int deleteByuserId(Long id);
	
	int updateByPrimaryKey(UserDigitalCurrencyInfo record);

}
