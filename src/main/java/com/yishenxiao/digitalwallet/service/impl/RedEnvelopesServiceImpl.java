package com.yishenxiao.digitalwallet.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.RedEnvelopes;
import com.yishenxiao.digitalwallet.dao.RedEnvelopesMapper;
import com.yishenxiao.digitalwallet.service.RedEnvelopesService;

@Service("redEnvelopesService")
public class RedEnvelopesServiceImpl implements RedEnvelopesService {

	@Autowired(required=false)@Qualifier("redEnvelopesMapper")
    private RedEnvelopesMapper redEnvelopesDao;
	
	@Override
	public int insertRedEnvelopesData(RedEnvelopes redEnvelopes) {
		return redEnvelopesDao.insert(redEnvelopes);
	}

	@Override
	public RedEnvelopes queryRedEnvelopesData(Long redEnvelopesId) {
		return redEnvelopesDao.selectByPrimaryKey(redEnvelopesId);
	}

	@Override
	public List<RedEnvelopes> queryByUserId(Long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return redEnvelopesDao.queryByUserId(map);
	}

	@Override
	public int updateData(Long redEnvelopesId, BigDecimal amount, Integer count) {
		RedEnvelopes redEnvelopes = new RedEnvelopes();
		redEnvelopes.setId(redEnvelopesId);
		redEnvelopes.setUsecount(count);
		return redEnvelopesDao.updateByPrimaryKeySelective(redEnvelopes);
	}

	@Override
	public int updateState(Long id, int i) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("state", i);
		return redEnvelopesDao.updateState(map);
	}

	@Override
	public List<RedEnvelopes> queryByRedisKey(String message) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("redisKey", message);
		return redEnvelopesDao.queryByRedisKey(map);
	}

	@Override
	public List<RedEnvelopes> queryByUserIdAndCurrency(Long userId, String currencyName) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("currencyName", currencyName);
		return redEnvelopesDao.queryByUserIdAndCurrency(map);
	}

}
