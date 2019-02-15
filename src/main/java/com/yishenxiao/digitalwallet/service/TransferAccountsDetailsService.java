package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.TransferAccountsDetails;

public interface TransferAccountsDetailsService {

	int insertData(TransferAccountsDetails transferAccountsDetails);

	List<TransferAccountsDetails> queryByUserId(Long userId);

}
