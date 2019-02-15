package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.dao.DigitalCurrencyInfoMapper;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;

@Service("digitalCurrencyInfoService")
public class DigitalCurrencyInfoServiceImpl implements DigitalCurrencyInfoService{
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoMapper")
	private DigitalCurrencyInfoMapper digitalCurrencyInfoDao;

	@Override
	public DigitalCurrencyInfo queryById(Long digitalcurrencyid) {
		return digitalCurrencyInfoDao.selectByPrimaryKey(digitalcurrencyid);
	}

	@Override
	public DigitalCurrencyInfo queryByDigitalcurrencyname(String currency) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currency", currency);
		return digitalCurrencyInfoDao.queryByDigitalcurrencyname(map);
	}

	@Override
	public int insertDigitalCurrencyData(String digitalCurrencyName) {
		DigitalCurrencyInfo digitalCurrencyInfo = new DigitalCurrencyInfo();
		digitalCurrencyInfo.setDigitalcurrencyname(digitalCurrencyName);
		return digitalCurrencyInfoDao.insert(digitalCurrencyInfo);
	}

	@Override
	public List<DigitalCurrencyInfo> queryByDigitalcurrencynameForList(String digitalCurrencyName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("digitalCurrencyName", digitalCurrencyName);
		return digitalCurrencyInfoDao.queryByDigitalcurrencynameForList(map);
	}

	@Override
	public List<DigitalCurrencyInfo> queryByCurrencyType(String digitalCurrencyName, int pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("digitalCurrencyName", digitalCurrencyName);
		map.put("pid", pid);
		return digitalCurrencyInfoDao.queryByCurrencyType(map);
	}

	@Override
	public List<DigitalCurrencyInfo> queryByDigitalCurrencyAddr(String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		return digitalCurrencyInfoDao.queryByDigitalCurrencyAddr(map);
	}

	@Override
	public List<DigitalCurrencyInfo> queryByData() {
		return digitalCurrencyInfoDao.queryByData();
	}

	@Override
	public DigitalCurrencyInfo queryByDigitalCurrencyId(Long digitalcurrencyinfoid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("digitalcurrencyinfoid", digitalcurrencyinfoid);
		return digitalCurrencyInfoDao.queryByDigitalCurrencyId(map);
	}
}
