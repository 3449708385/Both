package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.WechatFriend;

public interface WechatFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatFriend record);

    int insertSelective(WechatFriend record);

    WechatFriend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatFriend record);

    int updateByPrimaryKey(WechatFriend record);

	List<WechatFriend> queryWechatFriend();

	List<WechatFriend> queryWechatFriendByCOU(Map<String, Object> map);

	List<WechatFriend> queryByUserName(Map<String, Object> map);

	int queryByCounts();

	List<WechatFriend> queryWechatFriendByCounts(Map<String, Object> map);
}