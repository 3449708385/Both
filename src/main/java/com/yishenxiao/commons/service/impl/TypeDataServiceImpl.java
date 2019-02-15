package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.TypeData;
import com.yishenxiao.commons.dao.TypeDataMapper;
import com.yishenxiao.commons.service.TypeDataService;

@Service("typeDataService")
public class TypeDataServiceImpl implements TypeDataService {

	@Autowired(required=false)@Qualifier("typeDataMapper")
	private TypeDataMapper typeDataDao;

	@Override
	public List<TypeData> queryByNickName(String groupcategoryname) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("groupcategoryname", groupcategoryname);
		return typeDataDao.queryByNickName(map);
	}
}
