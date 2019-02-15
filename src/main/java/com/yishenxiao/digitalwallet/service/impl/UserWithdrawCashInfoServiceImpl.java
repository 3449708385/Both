package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;
import com.yishenxiao.digitalwallet.dao.UserWithdrawCashInfoMapper;
import com.yishenxiao.digitalwallet.service.UserWithdrawCashInfoService;

@Service("userWithdrawCashInfoService")
public class UserWithdrawCashInfoServiceImpl implements UserWithdrawCashInfoService {

	@Autowired(required = false)
	@Qualifier("userWithdrawCashInfoMapper")
	private UserWithdrawCashInfoMapper userWithdrawCashInfoDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userWithdrawCashInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserWithdrawCashInfo record) {
		return userWithdrawCashInfoDao.insert(record);
	}

	@Override
	public int insertSelective(UserWithdrawCashInfo record) {
		return userWithdrawCashInfoDao.insertSelective(record);
	}

	@Override
	public UserWithdrawCashInfo selectByPrimaryKey(Long id) {
		return userWithdrawCashInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserWithdrawCashInfo record) {
		return userWithdrawCashInfoDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserWithdrawCashInfo record) {
		return userWithdrawCashInfoDao.updateByPrimaryKey(record);
	}

	@Override
	public List<UserWithdrawCashInfo> queryUndisposedInfoByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userWithdrawCashInfoDao.queryUndisposedInfoByUserId(map);
	}

	@Override
	public List<UserWithdrawCashInfo> queryByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userWithdrawCashInfoDao.queryByUserId(map);
	}

	@Override
	public List<UserWithdrawCashInfo> queryByUserIdAndCurrencyId(Long userId, Long currencyNameId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalcurrencyinfoid", currencyNameId);
		return userWithdrawCashInfoDao.queryByUserIdAndCurrencyId(map);
	}

}
