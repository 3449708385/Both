package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.dao.UserDigitalAddrMapper;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;

@Service("userDigitalAddrService")
public class UserDigitalAddrServiceImpl implements UserDigitalAddrService {

	@Autowired(required=false)@Qualifier("userDigitalAddrMapper")
	private UserDigitalAddrMapper userDigitalAddrDao;
	
	@Override
	public int insertData(UserDigitalAddr userDigitalAddr) {
		return userDigitalAddrDao.insert(userDigitalAddr);
	}

	@Override
	public List<UserDigitalAddr> queryAddr(String transferOutAddr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferOutAddr", transferOutAddr);
		return userDigitalAddrDao.queryAddr(map);
	}

	@Override
	public UserDigitalAddr queryByData(Long userId, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyInfoId", id);
		return userDigitalAddrDao.queryByData(map);
	}

}
