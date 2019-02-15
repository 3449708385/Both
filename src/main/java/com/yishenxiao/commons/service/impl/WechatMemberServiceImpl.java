package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.WechatMember;
import com.yishenxiao.commons.dao.WechatMemberMapper;
import com.yishenxiao.commons.service.WechatMemberService;

@Service("wechatMemberService")
public class WechatMemberServiceImpl implements WechatMemberService {

	@Autowired(required=false)@Qualifier("wechatMemberMapper")
	private WechatMemberMapper wechatMemberDao;
	

	@Override
	public List<WechatMember> queryByGroupName(String fromgroup) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromgroup", fromgroup);
		return wechatMemberDao.queryByGroupName(map);
	}


	@Override
	public List<WechatMember> queryWechatMember() {
		return wechatMemberDao.queryWechatMember();
	}


	@Override
	public List<WechatMember> queryWechatMemberByCOU(int cou) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cou", cou);
		return wechatMemberDao.queryWechatMemberByCOU(map);
	}


	@Override
	public List<WechatMember> queryByParentUserName(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		return wechatMemberDao.queryByParentUserName(map);
	}


	@Override
	public List<WechatMember> queryByUserName(String from) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", from);
		return wechatMemberDao.queryByUserName(map);
	}


	@Override
	public int queryByCounts() {	
		return wechatMemberDao.queryByCounts();
	}


	@Override
	public List<WechatMember> queryWechatMemberByCounts(int start, int eventCounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("eventCounts", eventCounts);
		return wechatMemberDao.queryWechatMemberByCounts(map);
	}


	@Override
	public List<WechatMember> queryByGroupNickNameMD5(String nicknamemd5) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nicknamemd5", nicknamemd5);
		return wechatMemberDao.queryByGroupNickNameMD5(map);
	}
}
