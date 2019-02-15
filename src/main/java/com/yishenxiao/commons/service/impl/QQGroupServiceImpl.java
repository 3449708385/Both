package com.yishenxiao.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.QQGroup;
import com.yishenxiao.commons.dao.QQGroupMapper;
import com.yishenxiao.commons.service.QQGroupService;

@Service("qQGroupService")
public class QQGroupServiceImpl implements QQGroupService {

	@Autowired(required=false)@Qualifier("QQGroupMapper")
	private QQGroupMapper qQGroupMapper;
	
	@Override
	public List<QQGroup> queryQQGroupData() {
		return qQGroupMapper.queryQQGroupData();
	}
}
