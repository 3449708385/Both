package com.yishenxiao.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.Notice;
import com.yishenxiao.commons.dao.NoticeMapper;
import com.yishenxiao.commons.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired(required=false)@Qualifier("noticeMapper")
	private NoticeMapper noticeDao;
	
	@Override
	public List<Notice> queryByLastTime() {
		return noticeDao.queryByLastTime();
	}

	@Override
	public List<Notice> queryByLastTimeTypeOne() {
		return noticeDao.queryByLastTimeTypeOne();
	}

}
