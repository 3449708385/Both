package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.GroupCategory;

public interface GroupCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupCategory record);

    int insertSelective(GroupCategory record);

    GroupCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupCategory record);

    int updateByPrimaryKey(GroupCategory record);

	List<GroupCategory> queryByData();

	int updateGroupCategory(Map<String, Object> map);

	List<GroupCategory> queryByGroupName(Map<String, Object> map);
}