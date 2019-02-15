package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.CoinsList;

public interface CoinsListService {

	List<CoinsList> queryByUserId(Long userId);

}
