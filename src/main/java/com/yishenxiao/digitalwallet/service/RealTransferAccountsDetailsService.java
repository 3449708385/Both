package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.RealTransferAccountsDetails;

public interface RealTransferAccountsDetailsService {

	int deleteByPrimaryKey(Long id);

	int insert(RealTransferAccountsDetails record);

	int insertSelective(RealTransferAccountsDetails record);

	RealTransferAccountsDetails selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RealTransferAccountsDetails record);

	int updateByPrimaryKey(RealTransferAccountsDetails record);

	List<RealTransferAccountsDetails> queryUndisposedInfoByUserIdAndDigitalCurrencyId(Long userId,
			Long digitalCurrencyInfoId);

	List<RealTransferAccountsDetails> queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(Long userId,
			Long digitalCurrencyInfoId);

	List<RealTransferAccountsDetails> queryUndisposedInfoByUserId(Long userId);

}
