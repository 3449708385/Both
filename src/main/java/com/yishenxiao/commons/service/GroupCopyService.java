package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.GroupCopy;

public interface GroupCopyService {

	List<GroupCopy> queryByMD5Code(String groupnamecode);

}
