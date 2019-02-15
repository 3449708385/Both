package com.yishenxiao.usermanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.UserRelationRole;
import com.yishenxiao.usermanager.dao.UserRelationRoleMapper;
import com.yishenxiao.usermanager.service.UserRelationRoleService;

@Service("userRelationRoleService")
public class UserRelationRoleServiceImpl implements UserRelationRoleService {

	@Autowired(required=false)@Qualifier("userRelationRoleMapper")
	private UserRelationRoleMapper userRelationRoleDao;
	
	@Override
	public int insertUserRelationRole(UserRelationRole userRelationRole) {	
		return userRelationRoleDao.insert(userRelationRole);
	}

}
