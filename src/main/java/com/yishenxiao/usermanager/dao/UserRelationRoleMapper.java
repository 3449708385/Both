package com.yishenxiao.usermanager.dao;

import com.yishenxiao.usermanager.beans.UserRelationRole;

public interface UserRelationRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRelationRole record);

    int insertSelective(UserRelationRole record);

    UserRelationRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRelationRole record);

    int updateByPrimaryKey(UserRelationRole record);
}