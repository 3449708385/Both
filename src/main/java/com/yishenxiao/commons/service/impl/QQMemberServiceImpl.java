package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.QQMember;
import com.yishenxiao.commons.dao.QQMemberMapper;
import com.yishenxiao.commons.service.QQMemberService;

@Service("qQMemberService")
public class QQMemberServiceImpl implements QQMemberService {
	
	@Autowired(required=false)@Qualifier("QQMemberMapper")
	private QQMemberMapper qQMemberDao;

	@Override
	public int queryByCounts() {
		return qQMemberDao.queryByCounts();
	}

	@Override
	public List<QQMember> queryQQMemberByCounts(int start, int eventCounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("eventCounts", eventCounts);
		return qQMemberDao.queryQQMemberByCounts(map);
	}

	@Override
	public int queryByCountsGroupId(Long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return qQMemberDao.queryByCountsGroupId(map);
	}

	@Override
	public List<QQMember> queryByGroupId(Long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return qQMemberDao.queryByGroupId(map);
	}

}
