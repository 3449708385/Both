package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.TypeData;

public interface TypeDataMapper {
    int insert(TypeData record);

    int insertSelective(TypeData record);

	List<TypeData> queryByNickName(Map<String, Object> map);
}