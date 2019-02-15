package com.yishenxiao.usermanager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yishenxiao.commons.controller.DataController;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.GroupNew;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.dao.GroupMapper;
import com.yishenxiao.usermanager.dao.UserMapper;
import com.yishenxiao.usermanager.dao.UserRelationGroupMapper;
import com.yishenxiao.usermanager.service.GroupService;


@Service("groupService")
public class GroupServiceImpl implements GroupService {
  
	@Autowired(required=false)@Qualifier("groupMapper")
	private GroupMapper groupDao;
	
	@Autowired(required=false)@Qualifier("userMapper")
	private UserMapper userDao;
	
	@Autowired(required=false)@Qualifier("userRelationGroupMapper")
	private UserRelationGroupMapper userRelationGroupDao;
	
	private static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	@Override
	public int insertGroupList(List<Group> groupList) {
		return groupDao.insertGroupList(groupList);
	}

	@Override
	public List<Group> queryByNickName(String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		return groupDao.queryByNickName(map);
	}

	@Override
	public int insertData(Group sysGroup) {
		return groupDao.insert(sysGroup);
	}

	@Override
	public List<Group> queryByGCId(Long groupcategoryid, int cou) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupcategoryid", groupcategoryid);
		map.put("cou", cou);
		return groupDao.queryByGCId(map);
	}

	@Override
	public int updateGroupRCoefficient(Long groupId, Integer rdindex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("rdindex", rdindex);
		return groupDao.updateGroupRCoefficient(map);
	}

	@Override
	public Group queryById(Long groupId) {
		return groupDao.selectByPrimaryKey(groupId);
	}

	@Override
	public List<Group> queryByData() {
		return groupDao.queryByData();
	}

	@Override
	public int deleteData(String groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return groupDao.deleteData(map);
	}

	@Override
	public int updateGroupData(Long groupId, String groupDesc, String groupName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		if(!StringUtils.judgeBlank(groupDesc)){
		  map.put("groupDesc", groupDesc);
		}
		if(!StringUtils.judgeBlank(groupName)){
		  map.put("groupName", groupName);
		}
		return groupDao.updateGroupData(map);
	}

	@Override
	public List<Group> qureyData() {
		return groupDao.queryByData();
	}

	@Override
	public List<Group> queryByUserName(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		return groupDao.queryByUserName(map);
	}

	@Override
	public List<Group> queryByGroupCategoryId(String categoryId, Integer cou) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("cou", cou);
		return groupDao.queryByGroupCategoryId(map);
	}

	@Override
	public List<Group> qureyUserData(List<Long> groupIds) {
		return groupDao.qureyUserData(groupIds);
	}

	@Override
	public Group queryByeasemobId(String eId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eId", eId);
		return groupDao.queryByeasemobId(map);
	}

	@Override
	public int updateGroupHead(String easemobGroupId, String httpGroupHead) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("easemobGroupId", easemobGroupId);
		map.put("httpGroupHead", httpGroupHead);
		return groupDao.updateGroupHead(map);
	}

	@Override
	public List<Group> queryByList(List<String> easembList) {
		return groupDao.queryByList(easembList);
	}

	@Override
	public int updataGroupType(String nickname, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		map.put("id", id);
		return groupDao.updataGroupType(map);
	}

	@Override
	public int updataGroupData(String username, String nickname, String owner) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("nickname", nickname);
		map.put("owner", owner);
		return groupDao.updataGroupData(map);
	}

	@Override
	public List<Group> queryByGroupData(String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		return groupDao.queryByGroupData(map);
	}

	@Override
	public int updateGroupCount(String easemobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("easemobId", easemobId);
		return groupDao.updateGroupCount(map);
	}

	@Override
	public int updataGroupUserName(String nickname, String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		map.put("username", username);
		return groupDao.updataGroupUserName(map);
	}

	@Override
	public List<Group> queryByArray(String[] groups) {
		return groupDao.queryByArray(groups);
	}

	@Override
	public List<Group> queryByGroupNameCode(String target) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupNameCode", target);
		return groupDao.queryByGroupNameCode(map);
	}

	@Override
	public List<Group> queryByGroupName(String target) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName", target);
		return groupDao.queryByGroupName(map);
	}

	@Override
	public int updataGroupTaget(String groupnamecode, Long groupcategoryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupnamecode", groupnamecode);
		map.put("groupcategoryid", groupcategoryid);
		return groupDao.updataGroupTaget(map);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public Group queryByownr(String easemobId, String ownr,List<String> userName) {
		Group group = new Group();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("eId", easemobId);
		if(!"".equals(easemobId)){
			//根据环信群id查询群；
			group =  groupDao.queryByownr(maps);
		}
		if(userName != null){
			for (String username : userName) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", username);
				//根据用户名查询用户ID
				com.yishenxiao.usermanager.beans.User user =  userDao.queryByUserName(map);
				UserRelationGroup record = new UserRelationGroup();
				record.setGroupid(group.getId());             
				record.setUserid(user.getId());
				record.setGagstate(0);//0:为正常，1为禁言,这里我默认设置为0正常
				record.setDisturbstate(0);//0代表正常，1免打扰
				record.setCreatetime(new Date());
				record.setUpdatetime(new Date());
				int c=userRelationGroupDao.insert(record);
				if(c!=1){
					logger.error("userId:"+user.getId()+"  groupId:"+group.getId()+", 用户与群关联失败，请手动添加！");
				}
			}
		}
		return group;
	}

	@Override
	public List<Group> queryByGroupDataMD5(String nicknamemd5) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nicknamemd5", nicknamemd5);
		return groupDao.queryByGroupDataMD5(map);
	}

	@Override
	public int insertLessData(Group group) {
		return groupDao.insertSelective(group);
	}

	@Override
	public int updateGroupInfoData(Long id, String groupowner, Integer groupcount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", id);
		map.put("groupowner", groupowner);
		map.put("groupcount", groupcount);
		return groupDao.updateGroupInfoData(map);
	}

	@Override
	public int updateGroupHeadAndGroupname(String easemobgroupid, String groupicon, String groupname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("easemodGroupId", easemobgroupid);
		map.put("groupicon", groupicon);
		map.put("groupname", groupname);
		return groupDao.updateGroupHeadAndGroupname(map);
	}

	@Override
	public List<Group> queryByRealData() {
		return groupDao.queryByRealData();
	}
	
	@Override
	public List<Group> queryByRebootData() {
		return groupDao.queryByRebootData();
	}

	@Override
	public List<Group> queryByGroupCategoryIdCount(String categoryId, Integer cou, Integer count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("cou", cou);
		map.put("memberCount", count);
		return groupDao.queryByGroupCategoryIdCount(map);
	}
	
	@Override
	public List<Group> queryByGroupCategoryIdCountNew(String categoryId, Integer cou, Integer count, Integer limitCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("cou", cou);
		map.put("memberCount", count);
		map.put("limitCount", limitCount);
		return groupDao.queryByGroupCategoryIdCountNew(map);
	}

	@Override
	public List<Group> queryByGroupCodeNotNull() {
		return groupDao.queryByGroupCodeNotNull();
	}

	@Override
	public List<Group> queryByeasemobIdList(String eId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eId", eId);
		return groupDao.queryByeasemobIdList(map);
	}

	@Override
	public List<Group> queryByGroupCategoryIdCountAstrict(String categoryId, Integer cou, Integer count, Integer limitCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("cou", cou);
		map.put("memberCount", count);
		map.put("limitCount", limitCount);
		return groupDao.queryByGroupCategoryIdCountAstrict(map);
	}

	@Override
	public int updateGropCountSet(Long groupId, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("size", size);
		return groupDao.updateGropCountSet(map);
	}

	@Override
	public int queryByRealAndOfficialCount() {
		return groupDao.queryByRealAndOfficialCount();
	}

	@Override
	public List<Group> queryByTempData(int start, int eventCounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("eventCounts", eventCounts);
		return groupDao.queryByTempData(map);
	}
}
