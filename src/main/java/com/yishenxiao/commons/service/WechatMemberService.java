package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.WechatMember;

public interface WechatMemberService {

	List<WechatMember> queryByGroupName(String fromgroup);

	List<WechatMember> queryWechatMember();

	List<WechatMember> queryWechatMemberByCOU(int cou);

	List<WechatMember> queryByParentUserName(String username);

	List<WechatMember> queryByUserName(String from);

	int queryByCounts();

	List<WechatMember> queryWechatMemberByCounts(int i, int eventCounts);

	List<WechatMember> queryByGroupNickNameMD5(String nicknamemd5);

}
