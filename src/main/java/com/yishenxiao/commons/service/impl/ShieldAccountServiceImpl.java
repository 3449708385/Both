package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.ShieldAccount;
import com.yishenxiao.commons.dao.ShieldAccountMapper;
import com.yishenxiao.commons.service.ShieldAccountService;

@Service("shieldAccountService")
public class ShieldAccountServiceImpl implements ShieldAccountService {

	@Autowired(required = false)
	@Qualifier("shieldAccountMapper")
	private ShieldAccountMapper shieldAccountDao;
	
	@Override
	public int insertData(ShieldAccount shieldAccount) {
		return shieldAccountDao.insert(shieldAccount);
	}

	@Override
	public List<ShieldAccount> queryByPhoneList(String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		return shieldAccountDao.queryByPhoneList(map);
	}

}
