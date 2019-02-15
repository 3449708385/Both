package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.WechatMember;

public interface WechatMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatMember record);

    int insertSelective(WechatMember record);

    WechatMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatMember record);

    int updateByPrimaryKey(WechatMember record);

	List<WechatMember> queryByGroupName(Map<String, Object> map);

	List<WechatMember> queryWechatMember();

	List<WechatMember> queryWechatMemberByCOU(Map<String, Object> map);

	List<WechatMember> queryByParentUserName(Map<String, Object> map);

	List<WechatMember> queryByUserName(Map<String, Object> map);

	int queryByCounts();

	List<WechatMember> queryWechatMemberByCounts(Map<String, Object> map);

	List<WechatMember> queryByGroupNickNameMD5(Map<String, Object> map);
}