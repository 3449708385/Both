package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.dao.UserMapper;
import com.yishenxiao.usermanager.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired(required=false)@Qualifier("userMapper")
	private UserMapper userDao;
	
	@Override
	public int insertUser(User user) {		
		return userDao.insert(user);
	}

	@Override
	public List<User> queryUser(String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		return userDao.queryUser(map);
	}

	@Override
	public User queryByUserId(Long userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> queryByExtensionUserId(Long extensionUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extensionUserId", extensionUserId);
		return userDao.queryByExtensionUserId(map);
	}

	@Override
	public List<User> queryByPhoneDistinguish(String[] phoneArray) {
		return userDao.queryByPhoneDistinguish(phoneArray);
	}

	@Override
	public List<User> queryByPhoneNotInUser(String[] phoneArray) {
		return userDao.queryByPhoneNotInUser(phoneArray);
	}

	@Override
	public List<User> queryByType(int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		return userDao.queryByType(map);
	}

	@Override
	public User queryByAccount(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		return userDao.queryByAccount(map);
	}

	@Override
	public List<User> queryByUserNameList(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		return userDao.queryByUserNameList(map);
	}

	@Override
	public List<User> queryByUserIdS(List<Long> userIds) {
		return userDao.queryByUserIdS(userIds);
	}

	@Override
	public List<User> queryByPhoneDistinguishList(List<String> list) {
		return userDao.queryByPhoneDistinguishList(list);
	}

	@Override
	public int queryByAccountCount() {	
		return userDao.queryByAccountCount();
	}

	@Override
	public List<User> queryByAccountList(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		return userDao.queryByAccountList(account);
	}

	@Override
	public List<User> queryByAccountNotNull() {
		return userDao.queryByAccountNotNull();
	}

	@Override
	public int updateDate(User user) {
		return userDao.updateByPrimaryKey(user);
	}

	@Override
	public List<User> queryByUserIdList(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userDao.queryByUserIdList(map);
	}

	@Override
	public int deleteById(Long id) {
		return userDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<User> queryByUserIdListAndPhoneNotNULL(List<Long> userIdList){ 
		return userDao.queryByUserIdListAndPhoneNotNULL(userIdList);
	}
}
