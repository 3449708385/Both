package com.yishenxiao.usermanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.dao.UserScheduleMapper;
import com.yishenxiao.usermanager.service.UserScheduleService;

@Service("userScheduleService")
public class UserScheduleServiceImpl implements UserScheduleService{

	@Autowired(required=false)@Qualifier("userScheduleMapper")
	private UserScheduleMapper userScheduleDao;
	
	@Override
	public int insertUserSchedule(UserSchedule userSchedule) {
		return userScheduleDao.insert(userSchedule);
	}

	@Override
	public int updateWallet(UserSchedule parentUserSchedule) {
		return userScheduleDao.updateByPrimaryKey(parentUserSchedule);
	}

	@Override
	public int updateNickname(Long userId, String nickName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("nickName", nickName);
		return userScheduleDao.updateNickname(map);
	}

	@Override
	public int updatepaypw(Long userId, String paypw) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("paypw", paypw);
		return userScheduleDao.updatepaypw(map);
	}

	@Override
	public List<UserSchedule> queryByPaypw(Long id, String paypw) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("paypw", paypw);
		return userScheduleDao.queryByPaypw(map);
	}

	@Override
	public int updateHeadPriture(String filePath, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filePath", filePath);
		map.put("userId", userId);
		return userScheduleDao.updateHeadPriture(map);
	}

	@Override
	public int updateUserSchedule(UserSchedule userScheduletemp) {
		return userScheduleDao.updateByPrimaryKeySelective(userScheduletemp);
	}

	@Override
	public List<UserSchedule> queryByUserIdList(List<Long> userIds) {
		return userScheduleDao.queryByUserIdList(userIds);
	}

	@Override
	public List<UserSchedule> queryByPurseAddress(String purseAddress) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purseAddress", purseAddress);
		return userScheduleDao.queryByPurseAddress(map);
	}

	@Override
	public List<UserSchedule> queryByNickName(Long groupId, String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("nickname", nickname);
		return userScheduleDao.queryByNickName(map);
	}

	@Override
	public List<UserSchedule> queryByNickNameData(String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		return userScheduleDao.queryByNickNameData(map);
	}

	@Override
	public List<UserSchedule> queryByUserIdS(List<Long> userIds) {
		return userScheduleDao.queryByUserIdS(userIds);
	}

	@Override
	public List<UserSchedule> queryByUIdList(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userScheduleDao.queryByUIdList(map);
	}

	@Override
	public int deleteByuserId(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);
		return userScheduleDao.deleteByuserId(map);
	}

	@Override
	public List<UserSchedule> queryByTempUserHead(int start, int eventCounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("eventCounts", eventCounts);
		return userScheduleDao.queryByTempUserHead(map);
	}

	@Override
	public int queryByCount() {
		return userScheduleDao.queryByCount();
	}
}
