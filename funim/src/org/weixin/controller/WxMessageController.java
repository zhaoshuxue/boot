package org.weixin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin.service.WxMessageService;

import com.google.gson.GsonBuilder;

/**
 *
 */
@Controller
@RequestMapping("wxmsg")
public class WxMessageController {
	
	@Autowired
	private WxMessageService wxMessageService;
	
	
	/**
	 * 跳转到页面
	 */
	@RequestMapping("/wxmsggrid")
	public String toMiniuiImageGrid(){
		return "wechat/jsp/message_grid";
	}
	
	/**
	 * 
	 */
	@RequestMapping("getWxMessageList")
	@ResponseBody
	public void getWxMessageList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String pageIndex = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String sortField = request.getParameter("sidx");
		String sortOrder = request.getParameter("sord");
		
		Map<String, Object> map = wxMessageService.getWxMessageList(pageIndex, pageSize, sortField, sortOrder);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	
	
}
