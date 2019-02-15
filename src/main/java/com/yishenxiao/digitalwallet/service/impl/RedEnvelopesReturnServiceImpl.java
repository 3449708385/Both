package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.RedEnvelopesReturn;
import com.yishenxiao.digitalwallet.dao.RedEnvelopesReturnMapper;
import com.yishenxiao.digitalwallet.service.RedEnvelopesReturnService;


@Service("redEnvelopesReturnService")
public class RedEnvelopesReturnServiceImpl implements RedEnvelopesReturnService {

	@Autowired(required=false)@Qualifier("redEnvelopesReturnMapper")
	private RedEnvelopesReturnMapper redEnvelopesReturnDao;

	@Override
	public int insert(RedEnvelopesReturn redEnvelopesReturn) {
		return redEnvelopesReturnDao.insert(redEnvelopesReturn);
	}

	@Override
	public List<RedEnvelopesReturn> queryByUserId(Long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return redEnvelopesReturnDao.queryByUserId(map);
	}

	@Override
	public List<RedEnvelopesReturn> queryByUserIdAndCurrencyId(Long userId, Long digitalCurrencyInfoId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyInfoId", digitalCurrencyInfoId);
		return redEnvelopesReturnDao.queryByUserIdAndCurrencyId(map);
	}
	
}
