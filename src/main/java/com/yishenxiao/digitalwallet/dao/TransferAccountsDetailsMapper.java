package com.yishenxiao.digitalwallet.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.digitalwallet.beans.TransferAccountsDetails;

public interface TransferAccountsDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferAccountsDetails record);

    int insertSelective(TransferAccountsDetails record);

    TransferAccountsDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransferAccountsDetails record);

    int updateByPrimaryKey(TransferAccountsDetails record);

	List<TransferAccountsDetails> queryByUserId(Map<String, Object> map);
}