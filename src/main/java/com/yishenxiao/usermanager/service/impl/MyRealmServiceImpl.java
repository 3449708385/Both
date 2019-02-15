package com.yishenxiao.usermanager.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yishenxiao.usermanager.beans.Menu;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.service.MenuService;
import com.yishenxiao.usermanager.service.RoleRelationMenuService;
import com.yishenxiao.usermanager.service.RoleService;
import com.yishenxiao.usermanager.service.UserRelationRoleService;
import com.yishenxiao.usermanager.service.UserService;

/**
 * 认证
 */
public class MyRealmServiceImpl extends AuthorizingRealm {
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("menuService")
    private MenuService menuService;
    
    @Autowired(required=false)@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired(required=false)@Qualifier("userRelationRoleService")
	private UserRelationRoleService userRelationRoleService;
	
	@Autowired(required=false)@Qualifier("roleRelationMenuService")
	private RoleRelationMenuService roleRelationMenuService;
    
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User)principals.getPrimaryPrincipal();
			
	
		/*List<Menu> menuList = null;
		//系统管理员，拥有最高权限
		if(user.getId() != 1){
			menuList = menuService.queryUserPerms(user.getId());
		}else{
			menuList = menuService.queryAllPerms();
		}
		List<String> permsList = new ArrayList<>(menuList.size());
		for(Menu menu : menuList){
			//部分菜单权限为空
			if(StringUtils.isBlank(menu.getPerms())){
				continue;
			}
			permsList.add(menu.getPerms());
		}
        
		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		info.setStringPermissions(permsSet);*/
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials()); 
        //查询用户信息
        User user = userService.queryByAccount(account);
        //账号不存在
        if(user == null || user.getType()!=0) {
            throw new UnknownAccountException("账号或密码不正确！");
        }       
        if(!user.getPasswd().equals(new Sha256Hash(password).toHex())){
        	throw new UnknownAccountException("账号或密码不正确！");
        }
        //账号锁定
        if(user.getState() != 0){
        	throw new LockedAccountException("账号未激活,请进入邮箱激活！");
        }     
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
	}

}
