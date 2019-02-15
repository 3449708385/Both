package com.yishenxiao.digitalwallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.yishenxiao.digitalwallet.beans.RedEnvelopes;

public interface RedEnvelopesService {

	int insertRedEnvelopesData(RedEnvelopes redEnvelopes);

	RedEnvelopes queryRedEnvelopesData(Long redEnvelopesId);

	List<RedEnvelopes> queryByUserId(Long userId);

	int updateData(Long redEnvelopesId, BigDecimal amount, Integer count);

	int updateState(Long id, int i);

	List<RedEnvelopes> queryByRedisKey(String message);

	List<RedEnvelopes> queryByUserIdAndCurrency(Long userId, String currencyName);

}
