package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;
import com.yishenxiao.digitalwallet.dao.UserRedEnvelopesMapper;
import com.yishenxiao.digitalwallet.service.UserRedEnvelopesService;

@Service("userRedEnvelopesService")
public class UserRedEnvelopesServiceImpl implements UserRedEnvelopesService {

	@Autowired(required=false)@Qualifier("userRedEnvelopesMapper")
	private UserRedEnvelopesMapper userRedEnvelopesDao;
	
	@Override
	public int insertUserRedEnvelopesData(UserRedEnvelopes userRedEnvelopes) {
		return userRedEnvelopesDao.insert(userRedEnvelopes);
	}

	@Override
	public List<UserRedEnvelopes> queryByUIDREDID(Long userId, Long redEnvelopesId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("redEnvelopesId", redEnvelopesId);
		return userRedEnvelopesDao.queryByUIDREDID(map);
	}

	@Override
	public List<UserRedEnvelopes> queryByRedEnvelopesId(Long redEnvelopesId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("redEnvelopesId", redEnvelopesId);
		return userRedEnvelopesDao.queryByRedEnvelopesId(map);
	}

	@Override
	public List<UserRedEnvelopes> queryByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userRedEnvelopesDao.queryByUserId(map);
	}

	@Override
	public List<UserRedEnvelopes> queryByUserIdAndCurrency(Long userId, String currencyName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("currencyName", currencyName);
		return userRedEnvelopesDao.queryByUserIdAndCurrency(map);
	}
}
