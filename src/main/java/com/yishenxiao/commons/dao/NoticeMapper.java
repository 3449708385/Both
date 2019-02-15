package com.yishenxiao.commons.dao;

import java.util.List;

import com.yishenxiao.commons.beans.Notice;

public interface NoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);

	List<Notice> queryByLastTime();

	List<Notice> queryByLastTimeTypeOne();
}