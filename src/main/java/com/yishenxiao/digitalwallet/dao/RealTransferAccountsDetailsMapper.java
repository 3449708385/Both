package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.RealTransferAccountsDetails;

public interface RealTransferAccountsDetailsMapper {

	int deleteByPrimaryKey(Long id);

	int insert(RealTransferAccountsDetails record);

	int insertSelective(RealTransferAccountsDetails record);

	RealTransferAccountsDetails selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RealTransferAccountsDetails record);

	int updateByPrimaryKey(RealTransferAccountsDetails record);

	List<RealTransferAccountsDetails> queryUndisposedInfoByUserIdAndDigitalCurrencyId(Map<String, Object> map);

	List<RealTransferAccountsDetails> queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(Map<String, Object> map);

	List<RealTransferAccountsDetails> queryUndisposedInfoByUserId(Map<String, Object> map);

}