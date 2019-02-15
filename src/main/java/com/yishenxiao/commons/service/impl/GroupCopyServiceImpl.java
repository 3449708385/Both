package com.yishenxiao.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yishenxiao.commons.beans.GroupCopy;
import com.yishenxiao.commons.dao.GroupCopyMapper;
import com.yishenxiao.commons.dao.QQGroupMapper;
import com.yishenxiao.commons.service.GroupCopyService;

@Service("groupCopyService")
public class GroupCopyServiceImpl implements GroupCopyService {
   
	@Autowired(required=false)@Qualifier("groupCopyMapper")
	private GroupCopyMapper groupCopyDao;

	@Override
	public List<GroupCopy> queryByMD5Code(String groupnamecode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupnamecode", groupnamecode);
		return groupCopyDao.queryByMD5Code(map);
	}
	
}
