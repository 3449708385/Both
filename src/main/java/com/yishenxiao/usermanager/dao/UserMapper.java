package com.yishenxiao.usermanager.dao;

import java.util.List;
import java.util.Map;

import com.yishenxiao.usermanager.beans.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	List<User> queryUser(Map<String, Object> map);

	List<User> queryByExtensionUserId(Map<String, Object> map);

	List<User> queryByPhoneDistinguish(String[] phoneArray);

	User queryByUserName(Map<String, Object> map);

	List<User> queryByPhoneNotInUser(String[] phoneArray);

	List<User> queryByType(Map<String, Object> map);

	User queryByAccount(Map<String, Object> map);

	List<User> queryByUserNameList(Map<String, Object> map);

	List<User> queryByUserIdS(List<Long> userIds);

	List<User> queryByPhoneDistinguishList(List<String> list);

	int queryByAccountCount();

	List<User> queryByAccountList(String account);

	List<User> queryByAccountNotNull();

	List<User> queryByUserIdList(Map<String, Object> map);

	List<User> queryByUserIdListAndPhoneNotNULL(List<Long> userIdList);
}