package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.DistributionRecord;

public interface DistributionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DistributionRecord record);

    int insertSelective(DistributionRecord record);

    DistributionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DistributionRecord record);

    int updateByPrimaryKey(DistributionRecord record);

	List<DistributionRecord> queryByUserId(Map<String, Object> map);
}