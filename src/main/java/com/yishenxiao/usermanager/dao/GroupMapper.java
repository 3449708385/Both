package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.GroupNew;

public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

	int insertGroupList(List<Group> groupList);

	List<Group> queryByNickName(Map<String, Object> map);

	List<Group> queryByGCId(Map<String, Object> map);

	int updateGroupRCoefficient(Map<String, Object> map);

	List<Group> queryByData();

	int deleteData(Map<String, Object> map);

	int updateGroupData(Map<String, Object> map);

	List<Group> queryByUserName(Map<String, Object> map);

	List<Group> queryByGroupCategoryId(Map<String, Object> map);

	List<Group> qureyUserData(List<Long> groupIds);

	Group queryByeasemobId(Map<String, Object> map);

	int updateGroupHead(Map<String, Object> map);

	List<Group> queryByList(List<String> easembList);

	int updataGroupType(Map<String, Object> map);

	int updataGroupData(Map<String, Object> map);

	List<Group> queryByGroupData(Map<String, Object> map);

	int updateGroupCount(Map<String, Object> map);

	int updataGroupUserName(Map<String, Object> map);

	List<Group> queryByArray(String[] groups);

	List<Group> queryByGroupNameCode(Map<String, Object> map);

	List<Group> queryByGroupName(Map<String, Object> map);

	int updataGroupTaget(Map<String, Object> map);

	Group queryByownr(Map<String, Object> map);

	List<Group> queryByGroupDataMD5(Map<String, Object> map);

	int updateGroupInfoData(Map<String, Object> map);

	int updateGroupHeadAndGroupname(Map<String, Object> map);

	List<Group> queryByRealData();
	
	List<Group> queryByRebootData();

	List<Group> queryByGroupCategoryIdCount(Map<String, Object> map);

	List<Group> queryByGroupCodeNotNull();

	List<Group> queryByeasemobIdList(Map<String, Object> map);

	List<Group> queryByGroupCategoryIdCountNew(Map<String, Object> map);

	List<Group> queryByGroupCategoryIdCountAstrict(Map<String, Object> map);

	int updateGropCountSet(Map<String, Object> map);

	int queryByRealAndOfficialCount();

	List<Group> queryByTempData(Map<String, Object> map);

}