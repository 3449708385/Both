package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.UserRelationGroup;

public interface UserRelationGroupService {

	int insertList(List<UserRelationGroup> userRelationGroupList);

	List<UserRelationGroup> qureyByUserId(Long userId);

	List<UserRelationGroup> queryByGroupId(Long groupid);

	UserRelationGroup queryByData(Long groupId, Long userId);

	int deleteData(Long groupId, Long userId);

	List<UserRelationGroup> queryByDataByList(Long groupId, Long userId);

	int deleteGroupAllData(Long groupId);

	List<UserRelationGroup> queryByOneData(Long groupId, int i);

	List<UserRelationGroup> queryByDataList(Long groupId, Long userId);

	int insertData(UserRelationGroup userRelationGroup);
}
