package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.WechatFriend;
import com.yishenxiao.commons.dao.WechatFriendMapper;
import com.yishenxiao.commons.service.WechatFriendService;

@Service("wechatFriendService")
public class WechatFriendServiceImpl implements WechatFriendService{

	@Autowired(required=false)@Qualifier("wechatFriendMapper")
	private WechatFriendMapper wechatFriendDao;
	
	@Override
	public List<WechatFriend> queryWechatFriend() {
		return wechatFriendDao.queryWechatFriend();
	}

	@Override
	public List<WechatFriend> queryWechatFriendByCOU(int cou) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cou", cou);
		return wechatFriendDao.queryWechatFriendByCOU(map);
	}

	@Override
	public List<WechatFriend> queryByUserName(String from) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", from);
		return wechatFriendDao.queryByUserName(map);
	}

	@Override
	public int queryByCounts() {
		return wechatFriendDao.queryByCounts();
	}

	@Override
	public List<WechatFriend> queryWechatFriendByCounts(int start, int eventCounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("eventCounts", eventCounts);
		return wechatFriendDao.queryWechatFriendByCounts(map);
	}

}
