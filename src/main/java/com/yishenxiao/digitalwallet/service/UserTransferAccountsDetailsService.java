package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;

public interface UserTransferAccountsDetailsService {

	int deleteByPrimaryKey(Long id);

	int insert(UserTransferAccountsDetails record);

	int insertSelective(UserTransferAccountsDetails record);

	UserTransferAccountsDetails selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserTransferAccountsDetails record);

	int updateByPrimaryKey(UserTransferAccountsDetails record);

	List<UserTransferAccountsDetails> queryByUserId(Long userId);

	List<UserTransferAccountsDetails> queryTransferDataByUserId(Long userId);

	List<UserTransferAccountsDetails> queryTransferPassiveDataByUserId(Long userId);

	List<UserTransferAccountsDetails> queryRechargeDataByUserId(Long userId);

	List<UserTransferAccountsDetails> queryTransferDataByUserIdAndCurrency(Long userId, Long currencyNameId);

	List<UserTransferAccountsDetails> queryTransferPassiveDataByUserIdAndCurrency(Long userId, Long currencyNameId);

	List<UserTransferAccountsDetails> queryRechargeDataByUserIdAndCurrency(Long userId, Long currencyNameId);

}
