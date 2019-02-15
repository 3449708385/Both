package com.yishenxiao.digitalwallet.dao;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.MoneyData;

public interface MoneyDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MoneyData record);

    int insertSelective(MoneyData record);

    MoneyData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MoneyData record);

    int updateByPrimaryKey(MoneyData record);

	List<MoneyData> queryByData();
}