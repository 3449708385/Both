package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;

public interface UserRedEnvelopesService {

	int insertUserRedEnvelopesData(UserRedEnvelopes userRedEnvelopes);

	List<UserRedEnvelopes> queryByUIDREDID(Long userId, Long redEnvelopesId);

	List<UserRedEnvelopes> queryByRedEnvelopesId(Long redEnvelopesId);

	List<UserRedEnvelopes> queryByUserId(Long userId);

	List<UserRedEnvelopes> queryByUserIdAndCurrency(Long userId, String currencyName);

}
