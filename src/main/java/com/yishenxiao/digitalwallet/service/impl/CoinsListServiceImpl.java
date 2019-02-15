package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.CoinsList;
import com.yishenxiao.digitalwallet.dao.CoinsListMapper;
import com.yishenxiao.digitalwallet.service.CoinsListService;

@Service("coinsListService")
public class CoinsListServiceImpl implements CoinsListService {

	@Autowired(required=false)@Qualifier("coinsListMapper")
	private CoinsListMapper coinsListDao;
	
	@Override
	public List<CoinsList> queryByUserId(Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		return coinsListDao.queryByUserId(map);
	}

}
