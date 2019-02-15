package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.Menu;
import com.yishenxiao.usermanager.dao.MenuMapper;
import com.yishenxiao.usermanager.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired(required=false)@Qualifier("menuMapper")
	private MenuMapper menuMapper;
	
/*	@Override
	public List<Menu> queryUserPerms(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return menuMapper.queryMenuByUserId(map);
	}

	@Override
	public List<Menu> queryAllPerms() {
		return menuMapper.queryAllData();
	}
*/
}
