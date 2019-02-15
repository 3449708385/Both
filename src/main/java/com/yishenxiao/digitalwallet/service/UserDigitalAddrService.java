package com.yishenxiao.digitalwallet.service;

import java.util.List;

import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;

public interface UserDigitalAddrService {

	int insertData(UserDigitalAddr userDigitalAddr);

	List<UserDigitalAddr> queryAddr(String transferOutAddr);

	UserDigitalAddr queryByData(Long userId, Long id);

}
