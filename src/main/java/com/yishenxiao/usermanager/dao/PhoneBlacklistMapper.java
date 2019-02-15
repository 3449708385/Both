package com.yishenxiao.usermanager.dao;

import com.yishenxiao.usermanager.beans.PhoneBlacklist;

public interface PhoneBlacklistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PhoneBlacklist record);

    int insertSelective(PhoneBlacklist record);

    PhoneBlacklist selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PhoneBlacklist record);

    int updateByPrimaryKey(PhoneBlacklist record);
}