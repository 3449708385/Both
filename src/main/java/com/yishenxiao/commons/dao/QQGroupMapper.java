package com.yishenxiao.commons.dao;

import java.util.List;

import com.yishenxiao.commons.beans.QQGroup;

public interface QQGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QQGroup record);

    int insertSelective(QQGroup record);

    QQGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QQGroup record);

    int updateByPrimaryKey(QQGroup record);

	List<QQGroup> queryQQGroupData();
}