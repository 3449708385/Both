package com.yishenxiao.commons.dao;

import java.util.List;

import com.yishenxiao.commons.beans.Version;

public interface VersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKeyWithBLOBs(Version record);

    int updateByPrimaryKey(Version record);

	List<Version> queryByLastTime();
}