package com.yishenxiao.usermanager.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatGroupService;
import com.yishenxiao.commons.service.WechatMemberService;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("update")
public class UpdateDataServiceImpl {
	
	@Autowired(required=false)@Qualifier("wechatGroupService")
	private WechatGroupService wechatGroupService;
	
	@Autowired(required=false)@Qualifier("wechatFriendService")
	private WechatFriendService wechatFriendService;
	
	@Autowired(required=false)@Qualifier("wechatMemberService")
	private WechatMemberService wechatMemberService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	
	/**
	 * @throws IOException 
	 * @Info 用户获得群的数据
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateInfo", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo updateInfo(){
		return ReturnInfo.ok();
    }
}
