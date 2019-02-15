package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.ShieldAccount;

public interface ShieldAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShieldAccount record);

    int insertSelective(ShieldAccount record);

    ShieldAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShieldAccount record);

    int updateByPrimaryKey(ShieldAccount record);

	List<ShieldAccount> queryByPhoneList(Map<String, Object> map);
}