package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.CoinsList;

public interface CoinsListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CoinsList record);

    int insertSelective(CoinsList record);

    CoinsList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CoinsList record);

    int updateByPrimaryKey(CoinsList record);

	List<CoinsList> queryByUserId(Map<String,Object> map);
}