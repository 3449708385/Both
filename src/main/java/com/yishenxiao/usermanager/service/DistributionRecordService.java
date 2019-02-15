package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.DistributionRecord;

public interface DistributionRecordService {

	int insert(DistributionRecord distributionRecord);

	List<DistributionRecord> queryByUserId(Long userId, Long dataId, Integer count);

}
