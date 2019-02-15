package com.yishenxiao.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.Version;
import com.yishenxiao.commons.dao.VersionMapper;
import com.yishenxiao.commons.service.VersionService;

@Service("versionService")
public class VersionServiceImpl implements VersionService {

	@Autowired(required=false)@Qualifier("versionMapper")
	private VersionMapper versionDao;
	
	@Override
	public List<Version> queryByLastTime() {
		return versionDao.queryByLastTime();
	}

}
