package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;

public interface UserWithdrawCashInfoService {

	int deleteByPrimaryKey(Long id);

	int insert(UserWithdrawCashInfo record);

	int insertSelective(UserWithdrawCashInfo record);

	UserWithdrawCashInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserWithdrawCashInfo record);

	int updateByPrimaryKey(UserWithdrawCashInfo record);

	List<UserWithdrawCashInfo> queryUndisposedInfoByUserId(Long userId);

	List<UserWithdrawCashInfo> queryByUserId(Long userId);

	List<UserWithdrawCashInfo> queryByUserIdAndCurrencyId(Long userId, Long currencyNameId);

}
