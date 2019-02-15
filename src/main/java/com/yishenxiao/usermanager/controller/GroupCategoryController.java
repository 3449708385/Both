package com.yishenxiao.usermanager.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.usermanager.beans.GroupCategory;
import com.yishenxiao.usermanager.beans.postbean.CreateGroupCategoryBean;
import com.yishenxiao.usermanager.beans.postbean.DeleteGroupCategoryBean;
import com.yishenxiao.usermanager.beans.postbean.UpdateGroupCategoryBean;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.MenuService;
import com.yishenxiao.usermanager.service.RoleService;
import com.yishenxiao.usermanager.service.UserRelationRoleService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("groupCategory")
public class GroupCategoryController {
	private static Logger logger = LoggerFactory.getLogger(GroupCategoryController.class);
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("menuService")
    private MenuService menuService;
	
	@Autowired(required=false)@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired(required=false)@Qualifier("userRelationRoleService")
	private UserRelationRoleService userRelationRoleService;
	
	@Autowired(required=false)@Qualifier("groupCategoryService")
	private GroupCategoryService groupCategoryService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	/**
	 * @info 增加群分类
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="createGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo createGroupCategory(@RequestBody CreateGroupCategoryBean createGroupCategoryBean){
		String groupcategoryname = createGroupCategoryBean.getGroupcategoryname();
		String groupdescribe = createGroupCategoryBean.getGroupdescribe();
		Integer rdindex = createGroupCategoryBean.getRdindex();
		if(StringUtils.judgeBlank(groupcategoryname) || StringUtils.judgeBlank(groupdescribe)){
			return ReturnInfo.error("参数错误！");
		}
		if(rdindex==null){
			return ReturnInfo.error("参数错误！");
		}
		List<GroupCategory> groupCategoryList= groupCategoryService.queryByGroupName(groupcategoryname);
		if(groupCategoryList.size()>=1){
			return ReturnInfo.error("存在同名分类！");
		}
		GroupCategory groupCategory = new GroupCategory();
		groupCategory.setClassificationdescribe(groupdescribe);
		groupCategory.setCreatetime(new Date());
		groupCategory.setGroupcategoryname(groupcategoryname);
		groupCategory.setRdindex(rdindex);
		groupCategory.setState(0);
		groupCategory.setUpdatetime(new Date());
		groupCategoryService.insertData(groupCategory);
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 删除群分类
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="deleteGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo deleteGroupCategory(@RequestBody DeleteGroupCategoryBean deleteGroupCategoryBean){
		Long groupcategoryId=deleteGroupCategoryBean.getGroupcategoryId();
		if(groupcategoryId==null){
			return ReturnInfo.error("参数错误！");
		}
		GroupCategory groupCategory = groupCategoryService.queryById(groupcategoryId);
		if(groupCategory==null){
			return ReturnInfo.error("参数错误！");
		}
		groupCategoryService.deleteGroupCategory(groupcategoryId);
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 修改群分类
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="updateGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo updateGroupCategory(@RequestBody UpdateGroupCategoryBean updateGroupCategoryBean){
		Long groupcategoryId=updateGroupCategoryBean.getGroupcategoryId();
		String groupcategoryname=updateGroupCategoryBean.getGroupcategoryname();
		String groupdescribe=updateGroupCategoryBean.getGroupdescribe();
		Integer rdindex=updateGroupCategoryBean.getRdindex();
		if(groupcategoryId==null || StringUtils.judgeBlank(groupcategoryname) && StringUtils.judgeBlank(groupdescribe) && rdindex==null){
			return ReturnInfo.error("参数错误！");
		}
		GroupCategory groupCategory = groupCategoryService.queryById(groupcategoryId);
		if(groupCategory==null){
			return ReturnInfo.error("参数错误！");
		}
		groupCategoryService.updateGroupCategory(groupcategoryId, groupcategoryname, groupdescribe, rdindex);
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 得到群分类列表
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getGroupCategory", method=RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ReturnInfo getGroupCategory(){
		List<GroupCategory> groupCategoryList = groupCategoryService.queryByData();
		if(groupCategoryList.size()==0){
			logger.info("数据库暂无群分类数据！");
		}
		return ReturnInfo.info(200,groupCategoryList);	
	}
	
	/**
	 * @info 得到预创群分类的推荐系数列表
	 * @return 按从大到小的顺序排序
	 */
/*	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("getGuideGroupCategoryRdindexList")
	public @ResponseBody ReturnInfo getGuideGroupCategoryRdindexList(){
		List<Integer> rdindexList = new ArrayList<Integer>();
		List<GroupCategory> groupCategoryList = groupCategoryService.queryByData();
		for(GroupCategory groupCategory:groupCategoryList){
			rdindexList.add(groupCategory.getRdindex());
		}
		return ReturnInfo.info(200, rdindexList);	
	}*/

}
