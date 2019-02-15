package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.usermanager.beans.UserRelationGroup;
import com.yishenxiao.usermanager.dao.UserRelationGroupMapper;
import com.yishenxiao.usermanager.service.UserRelationGroupService;

@Service("userRelationGroupService")
public class UserRelationGroupServiceImpl implements UserRelationGroupService {

	@Autowired(required=false)@Qualifier("userRelationGroupMapper")
	private UserRelationGroupMapper userRelationGroupDao;
	
	private static Lock lock = new ReentrantLock();// 锁对象 
	
	@Override
	public int insertList(List<UserRelationGroup> userRelationGroupList) {
		int dataSize = 0;
		for(int i=0;i<userRelationGroupList.size();i++){
			dataSize=dataSize+insertUserRelationGroup(userRelationGroupList.get(i));
		}
		return dataSize;
	}

	@Override
	public List<UserRelationGroup> qureyByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userRelationGroupDao.qureyByUserId(map);
	}
	
	@Override
	public List<UserRelationGroup> queryByGroupId(Long groupid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupid", groupid);
		return userRelationGroupDao.queryByGroupId(map);
	}

	private int insertUserRelationGroup(UserRelationGroup userRelationGroup) {
		int size=0;
		List<UserRelationGroup> userRelationGroupList =queryByGroupId(userRelationGroup.getGroupid());
		Integer groupCount=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("easemobgroupCount"));
		if(userRelationGroupList.size()>=groupCount){
		    return size;
		}else{
			size=userRelationGroupDao.insert(userRelationGroup);
		}
		return size;
	}

	@Override
	public UserRelationGroup queryByData(Long groupId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("userId", userId);
		return userRelationGroupDao.queryByData(map);
	}

	@Override
	public List<UserRelationGroup> queryByDataByList(Long groupId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("userId", userId);
		return userRelationGroupDao.queryByDataByList(map);
	}
	
	@Override
	public int deleteData(Long groupId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("userId", userId);
		return userRelationGroupDao.deleteData(map);
	}

	@Override
	public int deleteGroupAllData(Long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return userRelationGroupDao.deleteGroupAllData(map);
	}

	@Override
	public List<UserRelationGroup> queryByOneData(Long groupId, int i) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("cou", i);
		return userRelationGroupDao.queryByOneData(map);
	}

	@Override
	public List<UserRelationGroup> queryByDataList(Long groupId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("userId", userId);
		return userRelationGroupDao.queryByDataList(map);
	}

	@Override
	public int insertData(UserRelationGroup userRelationGroup) {
		return userRelationGroupDao.insert(userRelationGroup);
	}
}
