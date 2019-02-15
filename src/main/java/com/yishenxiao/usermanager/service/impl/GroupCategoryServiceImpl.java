package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.usermanager.beans.GroupCategory;
import com.yishenxiao.usermanager.dao.GroupCategoryMapper;
import com.yishenxiao.usermanager.service.GroupCategoryService;

@Service("groupCategoryService")
public class GroupCategoryServiceImpl implements GroupCategoryService{
	
	@Autowired(required=false)@Qualifier("groupCategoryMapper")
	private GroupCategoryMapper groupCategoryDao;

	@Override
	public List<GroupCategory> queryByData() {
		return groupCategoryDao.queryByData();
	}

	@Override
	public int insertData(GroupCategory groupCategory) {
		return groupCategoryDao.insert(groupCategory);
	}

	@Override
	public int deleteGroupCategory(Long groupcategoryId) {
		return groupCategoryDao.deleteByPrimaryKey(groupcategoryId);
	}

	@Override
	public int updateGroupCategory(Long groupcategoryId, String groupcategoryname, String groupdescribe,
			Integer rdindex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupcategoryId", groupcategoryId);
		if(!StringUtils.judgeBlank(groupcategoryname)){
			map.put("groupcategoryname", groupcategoryname);
		}
		if(!StringUtils.judgeBlank(groupdescribe)){
			map.put("groupdescribe", groupdescribe);
		}
		if(rdindex!=null){
			map.put("rdindex", rdindex);
		}
		return groupCategoryDao.updateGroupCategory(map);
	}

	@Override
	public List<GroupCategory> queryByGroupName(String groupcategoryname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupcategoryname", groupcategoryname);
		return groupCategoryDao.queryByGroupName(map);
	}

	@Override
	public GroupCategory queryById(Long groupcategoryId) {
		return groupCategoryDao.selectByPrimaryKey(groupcategoryId);
	}
}
