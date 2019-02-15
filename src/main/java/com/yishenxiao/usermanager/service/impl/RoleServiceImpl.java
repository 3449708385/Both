package com.yishenxiao.usermanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.Role;
import com.yishenxiao.usermanager.dao.RoleMapper;
import com.yishenxiao.usermanager.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired(required=false)@Qualifier("roleMapper")
	private RoleMapper roleDao;
	
	@Override
	public Role queryById(Long id) {
		return roleDao.selectByPrimaryKey(id);
	}

}
