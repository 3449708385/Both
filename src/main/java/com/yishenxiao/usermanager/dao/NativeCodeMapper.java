package com.yishenxiao.usermanager.dao;

import java.util.List;

import com.yishenxiao.usermanager.beans.NativeCode;

public interface NativeCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NativeCode record);

    int insertSelective(NativeCode record);

    NativeCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NativeCode record);

    int updateByPrimaryKey(NativeCode record);

	List<NativeCode> queryByData();
}