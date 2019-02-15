package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;

public interface UserScheduleService {

	int insertUserSchedule(UserSchedule userSchedule);

	int updateWallet(UserSchedule parentUserSchedule);

	int updateNickname(Long userId, String nickName);

	int updatepaypw(Long userId, String paypw);

	List<UserSchedule> queryByPaypw(Long id, String paypw);

	int updateHeadPriture(String filePath, Long userId);

	int updateUserSchedule(UserSchedule userScheduletemp);

	List<UserSchedule> queryByUserIdList(List<Long> userIds);

	List<UserSchedule> queryByPurseAddress(String purseAddress);

	List<UserSchedule> queryByNickName(Long groupId, String nickname);

	List<UserSchedule> queryByNickNameData(String nickname);

	List<UserSchedule> queryByUserIdS(List<Long> userIds);

	List<UserSchedule> queryByUIdList(Long userid);

	int deleteByuserId(Long id);

	List<UserSchedule> queryByTempUserHead(int start, int eventCounts);

	int queryByCount();

}
