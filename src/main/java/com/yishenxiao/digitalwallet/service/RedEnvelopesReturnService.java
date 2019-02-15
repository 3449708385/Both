package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.RedEnvelopesReturn;

public interface RedEnvelopesReturnService {

	int insert(RedEnvelopesReturn redEnvelopesReturn);

	List<RedEnvelopesReturn> queryByUserId(Long userId);

	List<RedEnvelopesReturn> queryByUserIdAndCurrencyId(Long userId, Long digitalCurrencyInfoId);

}
