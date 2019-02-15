package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.WechatGroup;
import com.yishenxiao.commons.dao.WechatGroupMapper;
import com.yishenxiao.commons.service.WechatGroupService;

@Service("wechatGroupService")
public class WechatGroupServiceImpl implements WechatGroupService{

	@Autowired(required=false)@Qualifier("wechatGroupMapper")
	private WechatGroupMapper WechatGroupDao;
	
	
	@Override
	public List<WechatGroup> queryWechatGroupData() {
		return WechatGroupDao.queryWechatGroupData();
	}
	@Override
	public List<WechatGroup> queryByUserName(String target){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("target", target);
		return WechatGroupDao.queryByUserName(map);
	}
	@Override
	public List<WechatGroup> queryWechatGroupDataByCOU(int cou) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cou", cou);
		return WechatGroupDao.queryWechatGroupDataByCOU(map);
	}
	
	@Override
	public List<WechatGroup> queryWechatGroupDianzhongData() {
		return WechatGroupDao.queryWechatGroupDianzhongData();
	}

}
