package com.yishenxiao.digitalwallet.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.dao.UserDigitalCurrencyInfoMapper;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;

@Service("userDigitalCurrencyInfoService")
public class UserDigitalCurrencyInfoServiceImpl implements UserDigitalCurrencyInfoService {

	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoMapper")
	private UserDigitalCurrencyInfoMapper userDigitalCurrencyInfoDao;
	
	@Override
	public List<UserDigitalCurrencyInfo> queryUserDigitalCurrencyInfoData(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userDigitalCurrencyInfoDao.queryUserDigitalCurrencyInfoData(map);
	}

	@Override
	public int updateDigitalcurrencybalance(Long userId, Long digitalcurrencyid, Double amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalcurrencyid", digitalcurrencyid);
		map.put("amount", amount);
		return userDigitalCurrencyInfoDao.updateDigitalcurrencybalance(map);
	}

	@Override
	public UserDigitalCurrencyInfo queryByDigitalCurrencyId(Long digitalCurrencyId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", digitalCurrencyId);
		return userDigitalCurrencyInfoDao.queryByDigitalCurrencyId(map);
	}

	@Override
	public int updateByDigitalCurrency(Long digitalCurrencyId, Long userId, Double amount, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", digitalCurrencyId);
		map.put("amount", amount);
		map.put("type", type);
		return userDigitalCurrencyInfoDao.updateByDigitalCurrency(map);
	}

	@Override
	public int updatUserDigitalCurrencyData(Long userId, BigDecimal monery, Long digitalcurrencyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("monery", monery);
		map.put("digitalcurrencyId", digitalcurrencyId);
		return userDigitalCurrencyInfoDao.updatUserDigitalCurrencyData(map);
	}

	@Override
	public List<UserDigitalCurrencyInfo> queryByDigitalCurrencyIdList(Long digitalCurrencyId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", digitalCurrencyId);
		return userDigitalCurrencyInfoDao.queryByDigitalCurrencyIdList(map);
	}

	@Override
	public int insertUserDigitalCurrencyData(UserDigitalCurrencyInfo userDigitalCurrencyInfo) {
		return userDigitalCurrencyInfoDao.insert(userDigitalCurrencyInfo);
	}

	@Override
	public int updateData(Long userId, Long digitalCurrencyId, BigDecimal amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", digitalCurrencyId);
		map.put("amount", amount);
		return userDigitalCurrencyInfoDao.updateData(map);
	}

	@Override
	public int insertList(List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList) {
		return userDigitalCurrencyInfoDao.insertList(userDigitalCurrencyInfoList);
	}

	@Override
	public int updateUserData(Long userid, Long digitalcurrencyinfoid, Double foreignAccount, Double borrowFrom) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userid);
		map.put("digitalCurrencyId", digitalcurrencyinfoid);
		map.put("foreignAccount", foreignAccount);
		map.put("borrowFrom", borrowFrom);
		return userDigitalCurrencyInfoDao.updateUserData(map);
	}

	@Override
	public int updateForeignUserData(Long userId, Long id, Double balance) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", id);
		map.put("foreignAccount", balance);
		return userDigitalCurrencyInfoDao.updateForeignUserData(map);
	}

	@Override
	public int updateByDigitalBalance(Long digitalCurrencyId, Long userId, Double tempMonetary) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyId", digitalCurrencyId);
		map.put("amount", tempMonetary);
		return userDigitalCurrencyInfoDao.updateByDigitalBalance(map);
	}

	@Override
	public int deleteByuserId(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);
		return userDigitalCurrencyInfoDao.deleteByuserId(map);
	}

	@Override
	public int updateByPrimaryKey(UserDigitalCurrencyInfo record) {
		return userDigitalCurrencyInfoDao.updateByPrimaryKey(record);
	}
}
