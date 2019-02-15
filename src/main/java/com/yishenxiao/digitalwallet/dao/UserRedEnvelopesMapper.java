package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;

public interface UserRedEnvelopesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRedEnvelopes record);

    int insertSelective(UserRedEnvelopes record);

    UserRedEnvelopes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRedEnvelopes record);

    int updateByPrimaryKey(UserRedEnvelopes record);

    List<UserRedEnvelopes> queryByUIDREDID(Map<String, Object> map);

	List<UserRedEnvelopes> queryByRedEnvelopesId(Map<String, Object> map);

	List<UserRedEnvelopes> queryByUserId(Map<String, Object> map);

	List<UserRedEnvelopes> queryByUserIdAndCurrency(Map<String, Object> map);
}