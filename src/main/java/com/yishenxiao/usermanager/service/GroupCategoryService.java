package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.GroupCategory;

public interface GroupCategoryService {

	List<GroupCategory> queryByData();

	int insertData(GroupCategory groupCategory);

	int deleteGroupCategory(Long groupcategoryId);

	int updateGroupCategory(Long groupcategoryId, String groupcategoryname, String groupdescribe, Integer rdindex);

	List<GroupCategory> queryByGroupName(String groupcategoryname);

	GroupCategory queryById(Long groupcategoryId);

}