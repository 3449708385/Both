package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;

public interface UserScheduleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSchedule record);

    int insertSelective(UserSchedule record);

    UserSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSchedule record);//无法更新支付passwd

    int updateByPrimaryKey(UserSchedule record);//无法更新支付passwd

	UserSchedule queryByUserId(Map<String, Object> map);

	int updateNickname(Map<String, Object> map);//无法更新支付passwd

	int updatepaypw(Map<String, Object> map);

	List<UserSchedule> queryByPaypw(Map<String, Object> map);

	int updateHeadPriture(Map<String, Object> map);//无法更新支付passwd

	int updateUserInfo(Map<String, Object> map);//无法更新支付passwd

	List<UserSchedule> queryByUserIdList(List<Long> userIds);

	List<UserSchedule> queryByPurseAddress(Map<String, Object> map);

	List<UserSchedule> queryByNickName(Map<String, Object> map);

	List<UserSchedule> queryByNickNameData(Map<String, Object> map);

	List<UserSchedule> queryByUserIdS(List<Long> userIds);

	List<UserSchedule> queryByUIdList(Map<String, Object> map);

	int deleteByuserId(Map<String, Object> map);

	List<UserSchedule> queryByTempUserHead(Map<String, Object> map);

	int queryByCount();
}