package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.IpAddr;

public interface IpAddrMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IpAddr record);

    int insertSelective(IpAddr record);

    IpAddr selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IpAddr record);

    int updateByPrimaryKeyWithBLOBs(IpAddr record);

    int updateByPrimaryKey(IpAddr record);

	List<IpAddr> queryByIp(Map<String, Object> map);

	List<IpAddr> queryByIpData();
}