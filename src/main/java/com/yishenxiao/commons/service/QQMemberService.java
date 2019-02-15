package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.QQMember;

public interface QQMemberService {

	int queryByCounts();

	List<QQMember> queryQQMemberByCounts(int i, int eventCounts);

	int queryByCountsGroupId(Long groupId);

	List<QQMember> queryByGroupId(Long groupId);

}
