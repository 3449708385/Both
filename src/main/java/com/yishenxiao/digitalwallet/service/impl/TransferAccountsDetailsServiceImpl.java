package com.yishenxiao.digitalwallet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.digitalwallet.beans.TransferAccountsDetails;
import com.yishenxiao.digitalwallet.dao.TransferAccountsDetailsMapper;
import com.yishenxiao.digitalwallet.service.TransferAccountsDetailsService;

@Service("transferAccountsDetailsService")
public class TransferAccountsDetailsServiceImpl implements TransferAccountsDetailsService {
	
	@Autowired(required=false)@Qualifier("transferAccountsDetailsMapper")
	private TransferAccountsDetailsMapper transferAccountsDetailsDao;

	@Override
	public int insertData(TransferAccountsDetails transferAccountsDetails) {
		return transferAccountsDetailsDao.insert(transferAccountsDetails);
	}

	@Override
	public List<TransferAccountsDetails> queryByUserId(Long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return transferAccountsDetailsDao.queryByUserId(map);
	}

}