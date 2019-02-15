package com.yishenxiao.usermanager.dao;

import com.yishenxiao.usermanager.beans.Friends;

public interface FriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Friends record);

    int insertSelective(Friends record);

    Friends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);
}