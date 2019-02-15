package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;

public interface UserTransferAccountsDetailsMapper {

	int deleteByPrimaryKey(Long id);

	int insert(UserTransferAccountsDetails record);

	int insertSelective(UserTransferAccountsDetails record);

	UserTransferAccountsDetails selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserTransferAccountsDetails record);

	int updateByPrimaryKey(UserTransferAccountsDetails record);

	List<UserTransferAccountsDetails> queryByUserId(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryTransferDataByUserId(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryTransferPassiveDataByUserId(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryRechargeDataByUserId(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryTransferDataByUserIdAndCurrency(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryTransferPassiveDataByUserIdAndCurrency(Map<String, Object> map);

	List<UserTransferAccountsDetails> queryRechargeDataByUserIdAndCurrency(Map<String, Object> map);

}