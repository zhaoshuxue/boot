package com.funimg.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.funimg.core.manager.ClientManager;
import com.funimg.core.util.ContextHolderUtils;
import com.funimg.core.util.ImageUtil;
//import com.funimg.core.util.ImageUtil;
import com.funimg.core.util.IpUtil;
import com.funimg.core.util.JsonUtil;
import com.funimg.core.util.MD5Util;
import com.funimg.web.model.Client;
import com.funimg.web.model.UserModel;
import com.funimg.web.model.json.JsonModel;
import com.funimg.web.service.UserService;

@Controller
@RequestMapping("")
public class LoginController {
	
	@Autowired
	private UserService userService;
	/**
	 * 跳转到主页面
	 */
	@RequestMapping("/wechat")
	public ModelAndView location() {
		
		return new ModelAndView("/jsp/layout/Master");
	}
	
	/**
	 * 跳转到首页展示页面
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		
		return new ModelAndView("/index");
	}
	
	/**
	 * 跳转到注册页面
	 */
	@RequestMapping("/register")
	public ModelAndView register() {
		
		return new ModelAndView("/jsp/login/register");
	}
	
	
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = ContextHolderUtils.getSession();
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("pwd"));

		String userName = request.getParameter("name");
		String passWord = request.getParameter("pwd");

		UserModel user = new UserModel();
		user.setUserName(userName);
		user.setPassWord(MD5Util.md5(passWord));

		UserModel user2 = userService.login(user);
		if (user2 != null) {
			request.getSession().setAttribute("accountId", user2.getId());

			Client client = new Client();
			client.setUser(user2);
			client.setIp(IpUtil.getIpAddr(request));
//			client.setLogindatetime(DateUtil.timeFormat());
//			client.setPrivileges(userService.getUserPrivileges(user2.getId()));

//			ClientManager.getInstance().addClinet(session.getId(), client);
			return user2.getUserName();
		} else {
			request.getSession().setAttribute("userId", "no session");
			return "";

		}

	}
	/**
	 * 退出
	 * @param request
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public void logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		if (session != null) {
			ClientManager.getInstance().removeClinet(session.getId());
			session.invalidate();
		}

	}
	
	@RequestMapping("checkname")
	@ResponseBody
	public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String name = request.getParameter("username");
		JsonModel json = userService.checkUsernameIsExist(name);
		JsonUtil.objectToJson(json, response);
	}
	
	/**
	 * 用户注册
	 * @throws IOException 
	 */
	@RequestMapping("/signin")
	@ResponseBody
	public void signin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JsonModel jModel = new JsonModel();
		String userName = request.getParameter("name");
		String email = request.getParameter("email");
		String passWord = request.getParameter("pwd");
		
		String validateCode = request.getParameter("code");
		validateCode = validateCode.toLowerCase();
		Object code = request.getSession().getAttribute("imageCode");
		code = code.toString().toLowerCase();
//		
		if (validateCode.equals(code)) {
//			jModel = accountService.register(userName, email, passWord);
			if (jModel.isSuccess()) {
				request.getSession().setAttribute("accountId", jModel.getMsg());
				jModel.setMsg("注册成功");
			}else {
				jModel.setSuccess(false);
				jModel.setMsg("注册失败");
			}
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("验证码错误");
		}
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 生成验证码
	 */
	@RequestMapping("/validatecode")
	@ResponseBody
	public void CreateValidateCode(HttpServletRequest request,
			HttpServletResponse response) {

		// 1、调用组件，生成图片和验证码
		Map<String, BufferedImage> imageMap = ImageUtil.createImage();
		// 2、将验证码记录到session，后面验证要用
		String imageCode = imageMap.keySet().iterator().next();
		HttpSession session = request.getSession();
		session.setAttribute("imageCode", imageCode);
		// 3、将生成的图片转换成输入流，赋值给输出属性
		BufferedImage image = imageMap.get(imageCode);
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * 获得图片的值 System.out.println("生成的图片code ： ==  " + imageCode); Object
		 * code = request.getSession().getAttribute("imageCode");
		 * System.out.println("得到的code是 : ==   " + code);
		 */
	}
	
	
	
	
	
	
	
}
