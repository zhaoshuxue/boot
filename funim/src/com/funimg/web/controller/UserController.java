package com.funimg.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funimg.web.service.UserService;
/**
 * 用户
 * @author zsx
 *
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	/**
	 * 跳转到用户管理模块
	 */
	@RequestMapping("toUserManager")
	public String toUserManager(){
		return "jsp/user/userManager";
	}
	/**
	 * 跳转到用户列表页面
	 */
	@RequestMapping("touserShow")
	public String touserShow(){
		return "jsp/user/userShow";
	}
	/**
	 * 添加用户
	 */
//	@RequestMapping("/addUser")
//	@ResponseBody
//	public void addUser(User user, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		// 也可以按对象接受参数
//		JsonModel jModel = userService.addUser(user);
//		JsonUtil.objectToJson(jModel, response);
//
//	}

}
