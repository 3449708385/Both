package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.RedEnvelopesReturn;

public interface RedEnvelopesReturnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RedEnvelopesReturn record);

    int insertSelective(RedEnvelopesReturn record);

    RedEnvelopesReturn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RedEnvelopesReturn record);

    int updateByPrimaryKey(RedEnvelopesReturn record);

	List<RedEnvelopesReturn> queryByUserId(Map<String, Object> map);

	List<RedEnvelopesReturn> queryByUserIdAndCurrencyId(Map<String, Object> map);
}