package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;

public interface UserDigitalAddrMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDigitalAddr record);

    int insertSelective(UserDigitalAddr record);

    UserDigitalAddr selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDigitalAddr record);

    int updateByPrimaryKey(UserDigitalAddr record);

	List<UserDigitalAddr> queryAddr(Map<String, Object> map);

	UserDigitalAddr queryByData(Map<String, Object> map);
}