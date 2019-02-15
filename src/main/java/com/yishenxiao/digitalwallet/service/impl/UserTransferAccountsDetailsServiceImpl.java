package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;
import com.yishenxiao.digitalwallet.dao.UserTransferAccountsDetailsMapper;
import com.yishenxiao.digitalwallet.service.UserTransferAccountsDetailsService;

@Service("userTransferAccountsDetailsService")
public class UserTransferAccountsDetailsServiceImpl implements UserTransferAccountsDetailsService {

	@Autowired(required = false)
	@Qualifier("userTransferAccountsDetailsMapper")
	private UserTransferAccountsDetailsMapper userTransferAccountsDetailsDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userTransferAccountsDetailsDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserTransferAccountsDetails record) {
		return userTransferAccountsDetailsDao.insert(record);
	}

	@Override
	public int insertSelective(UserTransferAccountsDetails record) {
		return userTransferAccountsDetailsDao.insertSelective(record);
	}

	@Override
	public UserTransferAccountsDetails selectByPrimaryKey(Long id) {
		return userTransferAccountsDetailsDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserTransferAccountsDetails record) {
		return userTransferAccountsDetailsDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserTransferAccountsDetails record) {
		return userTransferAccountsDetailsDao.updateByPrimaryKey(record);
	}

	@Override
	public List<UserTransferAccountsDetails> queryByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userTransferAccountsDetailsDao.queryByUserId(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryTransferDataByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userTransferAccountsDetailsDao.queryTransferDataByUserId(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryTransferPassiveDataByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userTransferAccountsDetailsDao.queryTransferPassiveDataByUserId(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryRechargeDataByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userTransferAccountsDetailsDao.queryRechargeDataByUserId(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryTransferDataByUserIdAndCurrency(Long userId, Long currencyNameId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalcurrencyinfoid", currencyNameId);
		return userTransferAccountsDetailsDao.queryTransferDataByUserIdAndCurrency(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryTransferPassiveDataByUserIdAndCurrency(Long userId,
			Long currencyNameId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalcurrencyinfoid", currencyNameId);
		return userTransferAccountsDetailsDao.queryTransferPassiveDataByUserIdAndCurrency(map);
	}

	@Override
	public List<UserTransferAccountsDetails> queryRechargeDataByUserIdAndCurrency(Long userId, Long currencyNameId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalcurrencyinfoid", currencyNameId);
		return userTransferAccountsDetailsDao.queryRechargeDataByUserIdAndCurrency(map);
	}

}
