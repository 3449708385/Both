package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.RedEnvelopes;

public interface RedEnvelopesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RedEnvelopes record);

    int insertSelective(RedEnvelopes record);

    RedEnvelopes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RedEnvelopes record);

    int updateByPrimaryKey(RedEnvelopes record);

	List<RedEnvelopes> queryByUserId(Map<String, Object> map);

	int updateState(Map<String, Object> map);

	List<RedEnvelopes> queryByRedisKey(Map<String, Object> map);

	List<RedEnvelopes> queryByUserIdAndCurrency(Map<String, Object> map);
}