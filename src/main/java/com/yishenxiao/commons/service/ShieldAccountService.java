package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.ShieldAccount;

public interface ShieldAccountService {

	int insertData(ShieldAccount shieldAccount);

	List<ShieldAccount> queryByPhoneList(String phone);

}
