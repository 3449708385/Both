package com.yishenxiao.commons.service.impl.easemob;

import com.yishenxiao.commons.service.easemob.AuthTokenAPI;
import com.yishenxiao.commons.utils.easemob.TokenUtil;


public class EasemobAuthToken implements AuthTokenAPI{

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
