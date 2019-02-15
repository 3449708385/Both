package com.yishenxiao.commons.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.Function;
import com.yishenxiao.commons.beans.Notice;
import com.yishenxiao.commons.beans.Version;
import com.yishenxiao.commons.service.FunctionService;
import com.yishenxiao.commons.service.NoticeService;
import com.yishenxiao.commons.service.VersionService;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.service.GroupService;

@Controller
@RequestMapping("version")
public class VersionController {

	@Autowired(required = false)
	@Qualifier("versionService")
	private VersionService versionService;

	@Autowired(required = false)
	@Qualifier("functionService")
	private FunctionService functionService;
	
	@Autowired(required = false)
	@Qualifier("noticeService")
	private NoticeService noticeService;

	@Autowired(required = false)
	@Qualifier("groupService")
	private GroupService groupService;

	
	/**
	 * @Info更新群分类的数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getVersionInfo")
	public @ResponseBody ReturnInfo getVersionInfo() {
		List<Version> versionList = versionService.queryByLastTime();
		return ReturnInfo.info(200, versionList.get(0));
	}
	
	/**
	 * @Info更新群分类的数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getNotice")
	public @ResponseBody ReturnInfo getNotice() {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Version> versionList = versionService.queryByLastTime();
		map.put("version", versionList.get(0));
		List<Notice> noticeList = noticeService.queryByLastTime();
		map.put("notice", noticeList.get(0));
		return ReturnInfo.info(200, map);
	}
	
	/**
	 * @Info更新群分类的数据 改进版
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getNoticeNEW")
	public @ResponseBody ReturnInfo getNoticeNEW() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Version> versionList = versionService.queryByLastTime();
		map.put("version", versionList.get(0));
		List<Notice> noticeList = noticeService.queryByLastTime();
		map.put("notice", noticeList.get(0));
		Group group = groupService.queryByeasemobId("41561538756609");
		map.put("group", group);
		return ReturnInfo.info(200, map);
	}

	/**
	 * @Info获取功能是否展示数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getFunctionInfo")
	public @ResponseBody ReturnInfo getFunctionInfo() {
		List<Function> functionList = functionService.queryFunction();
		return ReturnInfo.info(200, functionList);
	}

}
