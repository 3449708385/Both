package com.yishenxiao.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.Function;
import com.yishenxiao.commons.dao.FunctionMapper;
import com.yishenxiao.commons.service.FunctionService;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

	@Autowired(required = false)
	@Qualifier("functionMapper")
	private FunctionMapper functionDao;

	@Override
	public List<Function> queryFunction() {
		return functionDao.queryFunction();
	}

}
