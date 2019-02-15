package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.ProtertiesData;

public interface ProtertiesDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProtertiesData record);

    int insertSelective(ProtertiesData record);

    ProtertiesData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProtertiesData record);

    int updateByPrimaryKey(ProtertiesData record);

	List<ProtertiesData> queryByDataKeyList(Map<String, Object> map);
}