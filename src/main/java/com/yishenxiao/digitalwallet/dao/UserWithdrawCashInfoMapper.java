package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;

public interface UserWithdrawCashInfoMapper {

	int deleteByPrimaryKey(Long id);

	int insert(UserWithdrawCashInfo record);

	int insertSelective(UserWithdrawCashInfo record);

	UserWithdrawCashInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserWithdrawCashInfo record);

	int updateByPrimaryKey(UserWithdrawCashInfo record);

	List<UserWithdrawCashInfo> queryUndisposedInfoByUserId(Map<String, Object> map);

	List<UserWithdrawCashInfo> queryByUserId(Map<String, Object> map);

	List<UserWithdrawCashInfo> queryByUserIdAndCurrencyId(Map<String, Object> map);

}