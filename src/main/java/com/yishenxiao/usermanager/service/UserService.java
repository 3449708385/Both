package com.yishenxiao.usermanager.service;

import java.util.List;

import com.yishenxiao.usermanager.beans.User;

public interface UserService {

	int insertUser(User user);

	User queryByUserId(Long userId);
    //根据手机查询用户
	List<User> queryUser(String phone);

	List<User> queryByExtensionUserId(Long extensionUserId);

	List<User> queryByPhoneDistinguish(String[] phoneArray);

	List<User> queryByPhoneNotInUser(String[] phoneArray);

	List<User> queryByType(int type);

	User queryByAccount(String account);

	List<User> queryByUserNameList(String username);

	List<User> queryByUserIdS(List<Long> userIds);

	List<User> queryByPhoneDistinguishList(List<String> phoneList);

	int queryByAccountCount();

	List<User> queryByAccountList(String account);

	List<User> queryByAccountNotNull();

	int updateDate(User user);

	List<User> queryByUserIdList(Long userId);

	int deleteById(Long id);

	List<User> queryByUserIdListAndPhoneNotNULL(List<Long> userIdList);

}
