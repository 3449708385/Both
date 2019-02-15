package com.yishenxiao.usermanager.dao;

import com.yishenxiao.usermanager.beans.RoleRelationMenu;

public interface RoleRelationMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleRelationMenu record);

    int insertSelective(RoleRelationMenu record);

    RoleRelationMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleRelationMenu record);

    int updateByPrimaryKey(RoleRelationMenu record);
}