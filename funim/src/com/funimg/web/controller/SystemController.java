package com.funimg.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.funimg.core.manager.ClientManager;
import com.funimg.core.util.JsonUtil;
import com.funimg.web.model.Client;
import com.funimg.web.model.json.JsonModel;
import com.funimg.web.util.TuLing;
import com.funimg.web.util.XiaoHua;
/**
 * 系统管理
 *
 */
@Controller
@RequestMapping("sys")
public class SystemController {
	/**
	 * 跳转到 链接地址 页面
	 */
	@RequestMapping("url")
	public String urlList(){
		return "jsp/sys/url_list";
	}
	@RequestMapping("ceshi")
	public String urlList2(){
		return "jsp/sys/url_list2";
	}
	/**
	 * 跳转到公共号信息页面
	 */
	@RequestMapping("toaccountInformation")
	public String toaccountInformation(){
		return "jsp/system/accountInformation";
	}
	/**
	 * 跳转到在线用户展示页面
	 */
	@RequestMapping("toonlineList")
	public String toonlineList(){
		return "jsp/system/onlineList";
	}
	

	/**
	 * 在线用户列表
	 */
	@RequestMapping("/online")
	public void datagridOnline(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Client> clients = new ArrayList<Client>();
//		clients.addAll(ClientManager.getInstance().getAllClient());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

//		Gson gson = new GsonBuilder().setPrettyPrinting().create();

//		response.getWriter().write(gson.toJson(clients));
//		response.getWriter().close();

	}
	
	/**
	 * 笑话
	 */
	@RequestMapping("/xiaohua")
	public ModelAndView updateAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String[] strings = XiaoHua.get();
		request.setAttribute("title", strings[0]);
		request.setAttribute("image", strings[1]);
		return new ModelAndView("/jsp/sys/xiaohua");
	}
	
	/**
	 * 跳转到聊天机器人页面
	 */
	@RequestMapping("rebot")
	public String toRebotChat(){
		return "jsp/rebot";
	}
	
	/**
	 * 聊天机器人
	 * @throws Exception 
	 */
	@RequestMapping("/rebotChat")
	public void rebotChat(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String question = request.getParameter("question");
		String result = question;
		System.out.println(result);
		if ("笑话".equals(question)) {
			result = "http://funimg.duapp.com/sys/xiaohua";
		}else {
			String tulingRes = TuLing.reboot(question);
			JSONObject jsonObject = JSON.parseObject(tulingRes);
			if (jsonObject.getIntValue("code") == 100000) {
				result = jsonObject.getString("text");
			}
		}
		System.out.println(result);
		
		response.getWriter().write(result);
		response.getWriter().close();
	}
	
	
	/**
	 */
	@RequestMapping("test")
	public String a(){
		return "jsp/test/a";
	}
	
	@RequestMapping("test2")
	public String b(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = request.getParameter("name");
		System.out.println(name);
		request.setAttribute("name", name);
		return "jsp/test/b";
	}
	
	@RequestMapping("test3")
	public String c(){
		return "jsp/test/c";
	}
	
	
	@RequestMapping("name")
	@ResponseBody
	public void name(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		
		request.getSession().removeAttribute("test_name");
		
		request.getSession().setAttribute("test_name", name);
		
		JsonModel jModel = new JsonModel();
		jModel.setSuccess(true);
		jModel.setMsg(name + "  已经存入session中" );
		JsonUtil.objectToJson(jModel, response);
	}
	
	@RequestMapping("test4")
	public String d(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
//		String name = request.getSession().getAttribute("test_name").toString();
//		request.setAttribute("name", name);
		
		return "jsp/test/d";
	}
	
	@RequestMapping("name2")
	@ResponseBody
	public void name2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String name = request.getSession().getAttribute("test_name").toString();
		JsonModel jModel = new JsonModel();
		jModel.setSuccess(true);
		jModel.setMsg("获取session中的值 为 ： " + name);
		JsonUtil.objectToJson(jModel, response);
	}
	
	
}
