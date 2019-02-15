package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.IpAddr;
import com.yishenxiao.commons.dao.IpAddrMapper;
import com.yishenxiao.commons.service.IpAddrService;

@Service("ipAddrService")
public class IpAddrServiceImpl implements IpAddrService {

	@Autowired(required=false)@Qualifier("ipAddrMapper")
    private IpAddrMapper ipAddrDao;
	
	@Override
	public List<IpAddr> queryByIp(String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ipAddr", ip);
		return ipAddrDao.queryByIp(map);
	}

	@Override
	public int updateByCount(IpAddr ipAddr) {
		ipAddr.setCount(ipAddr.getCount()+1);
		return ipAddrDao.updateByPrimaryKeyWithBLOBs(ipAddr);
	}

	@Override
	public List<IpAddr> queryByIpData() {
		return ipAddrDao.queryByIpData();
	}

	@Override
	public int unLockIp(IpAddr ipAddr) {
		ipAddr.setCount(0);
		return ipAddrDao.updateByPrimaryKey(ipAddr);
	}

	@Override
	public int insertData(IpAddr ipAddr) {
		return ipAddrDao.insert(ipAddr);
	}

}
