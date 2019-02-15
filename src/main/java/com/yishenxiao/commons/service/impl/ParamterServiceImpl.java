package com.yishenxiao.commons.service.impl;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.service.UserService;

public class ParamterServiceImpl implements HandlerInterceptor {

	private static ThreadLocal<Object> model = new ThreadLocal<>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*Map<String, String[]> params = request.getParameterMap();
		System.out.println(params.get("userId"));
		System.out.println(params.get("dataToken"));
		String userId = request.getParameter("userId");
		String dataToken = request.getParameter("dataToken");
		if(StringUtils.judgeBlank(dataToken)){
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			String json = JsonUtils.toJson(ReturnInfo.error("新版本数据加密升级，请前往www.both.im下载更新"));
			out.print(json);
			out.flush();
			out.close();
			return false;
		}else{
			UserService userService =(UserService)SpringContextUtils.getBean("userService"); 
			List<User> userList = userService.queryByUserIdList(Long.parseLong(userId));
			if(!userList.get(0).getUsertoken().equals(dataToken)){
				response.setContentType("text/html; charset=utf-8");
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				String json = JsonUtils.toJson(ReturnInfo.error("参数错误！"));
				out.print(json);
				out.flush();
				out.close();
				return false;
			}else{
				return true;
			}
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(modelAndView);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(model.get());
		HandlerMethod method = (HandlerMethod)handler; 
		System.out.println(response.getWriter().toString());
	}

}
