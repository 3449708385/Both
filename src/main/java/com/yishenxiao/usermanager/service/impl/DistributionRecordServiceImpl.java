package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.DistributionRecord;
import com.yishenxiao.usermanager.dao.DistributionRecordMapper;
import com.yishenxiao.usermanager.service.DistributionRecordService;

@Service("distributionRecordService")
public class DistributionRecordServiceImpl implements DistributionRecordService {

	@Autowired(required=false)@Qualifier("distributionRecordMapper")
	private DistributionRecordMapper distributionRecordMapper;
	
	@Override
	public int insert(DistributionRecord distributionRecord) {
		return distributionRecordMapper.insert(distributionRecord);
	}
	@Override
	public List<DistributionRecord> queryByUserId(Long userId, Long dataId, Integer count){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("dataId", dataId);
		map.put("count", count);
		return distributionRecordMapper.queryByUserId(map);
	}
}
