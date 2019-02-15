package com.yishenxiao.usermanager.dao;

import com.yishenxiao.usermanager.beans.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKeyWithBLOBs(Menu record);

    int updateByPrimaryKey(Menu record);
}