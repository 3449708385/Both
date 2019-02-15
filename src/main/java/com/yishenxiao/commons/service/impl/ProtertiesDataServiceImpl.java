package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.ProtertiesData;
import com.yishenxiao.commons.dao.ProtertiesDataMapper;
import com.yishenxiao.commons.service.ProtertiesDataService;

@Service("protertiesDataService")
public class ProtertiesDataServiceImpl implements ProtertiesDataService {

	@Autowired(required=false)@Qualifier("protertiesDataMapper")
	private ProtertiesDataMapper protertiesDataDao;
	
	@Override
	public List<ProtertiesData> queryByDataKeyList(String dataKey) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dataKey", dataKey);
		return protertiesDataDao.queryByDataKeyList(map);
	}

}
