package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.WechatFriend;

public interface WechatFriendService {

	List<WechatFriend> queryWechatFriend();

	List<WechatFriend> queryWechatFriendByCOU(int cou);

	List<WechatFriend> queryByUserName(String from);

	int queryByCounts();

	List<WechatFriend> queryWechatFriendByCounts(int i, int eventCounts);

}
