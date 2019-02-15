package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.GroupNew;
import com.yishenxiao.usermanager.beans.UserSchedule;

import io.swagger.client.model.UserName;

public interface GroupService {

	int insertGroupList(List<Group> groupList);

	List<Group> queryByNickName(String nickname);

	int insertData(Group sysGroup);

	List<Group> queryByGCId(Long groupcategoryid, int cou);

	int updateGroupRCoefficient(Long groupId, Integer rdindex);

	Group queryById(Long groupId);

	List<Group> queryByData();

	int deleteData(String groupId);

	int updateGroupData(Long groupId, String groupDesc, String groupName);

	List<Group> qureyData();

	List<Group> queryByUserName(String username);

	List<Group> queryByGroupCategoryId(String categoryId, Integer cou);

	List<Group> qureyUserData(List<Long> groupIds);

	Group queryByeasemobId(String eId);

	int updateGroupHead(String easemobGroupId, String httpGroupHead);

	List<Group> queryByList(List<String> easembList);

	int updataGroupType(String nickname, Long id);

	int updataGroupData(String username, String nickname, String owner);

	List<Group> queryByGroupData(String nickname);

	int updateGroupCount(String easemobId);

	int updataGroupUserName(String nickname, String username);

	List<Group> queryByArray(String[] groups);

	List<Group> queryByGroupNameCode(String target);

	List<Group> queryByGroupName(String target);

	int updataGroupTaget(String groupnamecode, Long groupcategoryid);

	Group queryByownr(String easemobId, String ownr, List<String> userName);

	List<Group> queryByGroupDataMD5(String nicknamemd5);

	int insertLessData(Group group);

	int updateGroupInfoData(Long id, String groupowner, Integer groupcount);

	int updateGroupHeadAndGroupname(String easemobgroupid, String groupicon, String groupname);

	List<Group> queryByRealData();//查询宝二爷的群，包括币圈官方

	List<Group> queryByGroupCategoryIdCount(String string, Integer cou, Integer count);
	
	List<Group> queryByGroupCategoryIdCountNew(String groupCategoryId, Integer cou, Integer count, Integer groupCount);

	List<Group> queryByGroupCodeNotNull();
	
	List<Group> queryByRebootData();//二爷的群

	List<Group> queryByeasemobIdList(String eId);

	List<Group> queryByGroupCategoryIdCountAstrict(String string, Integer cou, Integer count, Integer limitCount);//只显示一个官方群

	int updateGropCountSet(Long groupId, int size);

	int queryByRealAndOfficialCount();

	List<Group> queryByTempData(int start, int eventCounts);

}
