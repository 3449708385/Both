package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.UserRelationGroup;

public interface UserRelationGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRelationGroup record);

    int insertSelective(UserRelationGroup record);

    UserRelationGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRelationGroup record);

    int updateByPrimaryKey(UserRelationGroup record);

	int insertList(List<UserRelationGroup> userRelationGroupList);

	List<UserRelationGroup> qureyByUserId(Map<String, Object> map);

	List<UserRelationGroup> queryByGroupId(Map<String, Object> map);

	UserRelationGroup queryByData(Map<String, Object> map);

	int deleteData(Map<String, Object> map);

	List<UserRelationGroup> queryByDataByList(Map<String, Object> map);

	int deleteGroupAllData(Map<String, Object> map);

	List<UserRelationGroup> queryByOneData(Map<String, Object> map);

	List<UserRelationGroup> queryByDataList(Map<String, Object> map);
}