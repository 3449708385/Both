package com.yishenxiao.commons.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.beans.GroupCopy;

public interface GroupCopyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupCopy record);

    int insertSelective(GroupCopy record);

    GroupCopy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupCopy record);

    int updateByPrimaryKey(GroupCopy record);

	List<GroupCopy> queryByMD5Code(Map<String, Object> map);
}