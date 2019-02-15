package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.QQMember;

public interface QQMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QQMember record);

    int insertSelective(QQMember record);

    QQMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QQMember record);

    int updateByPrimaryKey(QQMember record);

	int queryByCounts();

	List<QQMember> queryQQMemberByCounts(Map<String, Object> map);

	int queryByCountsGroupId(Map<String, Object> map);

	List<QQMember> queryByGroupId(Map<String, Object> map);
}