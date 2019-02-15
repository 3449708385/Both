package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.WechatGroup;

public interface WechatGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatGroup record);

    int insertSelective(WechatGroup record);

    WechatGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatGroup record);

    int updateByPrimaryKey(WechatGroup record);

	List<WechatGroup> queryWechatGroupData();

	List<WechatGroup> queryByUserName(Map<String, Object> map);

	List<WechatGroup> queryWechatGroupDataByCOU(Map<String, Object> map);

	List<WechatGroup> queryWechatGroupDianzhongData();
}