package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.IpAddr;

public interface IpAddrService {

	List<IpAddr> queryByIp(String ip);

	int updateByCount(IpAddr ipAddr);

	List<IpAddr> queryByIpData();

	int unLockIp(IpAddr ipAddr);

	int insertData(IpAddr ipAddr);

}
