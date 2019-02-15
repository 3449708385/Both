package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.WechatGroup;

public interface WechatGroupService {

	List<WechatGroup> queryWechatGroupData();

	List<WechatGroup> queryByUserName(String target);

	List<WechatGroup> queryWechatGroupDataByCOU(int cou);

	List<WechatGroup> queryWechatGroupDianzhongData();

}
