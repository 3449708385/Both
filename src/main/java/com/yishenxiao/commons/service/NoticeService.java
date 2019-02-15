package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.Notice;

public interface NoticeService {

	List<Notice> queryByLastTime();

	List<Notice> queryByLastTimeTypeOne();

}
