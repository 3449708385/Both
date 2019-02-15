package com.yishenxiao.usermanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.NativeCode;
import com.yishenxiao.usermanager.dao.NativeCodeMapper;
import com.yishenxiao.usermanager.service.NativeCodeService;

@Service("nativeCodeService")
public class NativeCodeServiceImpl implements NativeCodeService {

	@Autowired(required=false)@Qualifier("nativeCodeMapper")
    private NativeCodeMapper nativeCodeDao;
	
	@Override
	public List<NativeCode> queryByData() {
		return nativeCodeDao.queryByData();
	}

}
