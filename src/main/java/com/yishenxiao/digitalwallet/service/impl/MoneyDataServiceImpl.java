package com.yishenxiao.digitalwallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.MoneyData;
import com.yishenxiao.digitalwallet.dao.MoneyDataMapper;
import com.yishenxiao.digitalwallet.service.MoneyDataService;

@Service("moneyDataService")
public class MoneyDataServiceImpl implements MoneyDataService {
	
	@Autowired(required=false)@Qualifier("moneyDataMapper")
	private MoneyDataMapper moneyDataDao;

	@Override
	public List<MoneyData> queryByData() {
		return moneyDataDao.queryByData();
	}

}
