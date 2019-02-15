package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.DataTest;
import com.yishenxiao.commons.dao.DataTestMapper;
import com.yishenxiao.commons.service.DataTestService;

@Service("dataTestService")
public class DataTestServiceImpl implements DataTestService{

	@Autowired(required=false)@Qualifier("dataTestMapper")
	private DataTestMapper dataTestDao;
	
	@Override
	public List<DataTest> queryByNickName(String groupcategoryname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupcategoryname", groupcategoryname);
		return dataTestDao.queryByNickName(map);
	}
    
}
