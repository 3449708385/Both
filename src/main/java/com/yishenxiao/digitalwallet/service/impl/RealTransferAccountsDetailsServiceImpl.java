package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.RealTransferAccountsDetails;
import com.yishenxiao.digitalwallet.dao.RealTransferAccountsDetailsMapper;
import com.yishenxiao.digitalwallet.service.RealTransferAccountsDetailsService;

@Service("realTransferAccountsDetailsService")
public class RealTransferAccountsDetailsServiceImpl implements RealTransferAccountsDetailsService {

	@Autowired(required = false)
	@Qualifier("realTransferAccountsDetailsMapper")
	private RealTransferAccountsDetailsMapper realTransferAccountsDetailsDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return realTransferAccountsDetailsDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RealTransferAccountsDetails record) {
		return realTransferAccountsDetailsDao.insert(record);
	}

	@Override
	public int insertSelective(RealTransferAccountsDetails record) {
		return realTransferAccountsDetailsDao.insertSelective(record);
	}

	@Override
	public RealTransferAccountsDetails selectByPrimaryKey(Long id) {
		return realTransferAccountsDetailsDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RealTransferAccountsDetails record) {
		return realTransferAccountsDetailsDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RealTransferAccountsDetails record) {
		return realTransferAccountsDetailsDao.updateByPrimaryKey(record);
	}

	@Override
	public List<RealTransferAccountsDetails> queryUndisposedInfoByUserIdAndDigitalCurrencyId(Long userId,
			Long digitalCurrencyInfoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyInfoId", digitalCurrencyInfoId);
		return realTransferAccountsDetailsDao.queryUndisposedInfoByUserIdAndDigitalCurrencyId(map);
	}

	@Override
	public List<RealTransferAccountsDetails> queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(Long userId,
			Long digitalCurrencyInfoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("digitalCurrencyInfoId", digitalCurrencyInfoId);
		return realTransferAccountsDetailsDao.queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(map);
	}

	@Override
	public List<RealTransferAccountsDetails> queryUndisposedInfoByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return realTransferAccountsDetailsDao.queryUndisposedInfoByUserId(map);
	}

}
