package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.DataTest;

public interface DataTestMapper {
    int insert(DataTest record);

    int insertSelective(DataTest record);

	List<DataTest> queryByNickName(Map<String, Object> map);
}