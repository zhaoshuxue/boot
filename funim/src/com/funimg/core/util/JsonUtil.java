package com.funimg.core.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 转换Json
 * @author zsx
 *
 */
public class JsonUtil {

	
	/**
	 * 利用fastjson将object转化为json字符串
	 * @param object
	 * @param response
	 * @throws IOException 
	 */
	public static void objectToJson(Object object, HttpServletResponse response) throws IOException {
		String result = JSON.toJSONString(object);
		System.out.println(result);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(result);
		response.getWriter().close();
	}
	
	/**
	 * 利用Gson将object转化为json字符串
	 * @param object
	 * @param response
	 * @throws IOException 
	 */
	public static void objectToGsonJson(Object object, Boolean tobeBeautify, HttpServletResponse response) throws IOException {
		Gson gson = null;
		String result = "";
		 if(tobeBeautify){//返回美化后的 JSON 字符串
			gson = new GsonBuilder().setPrettyPrinting().create();
		} else {
			gson = new Gson();
		}
		result = gson.toJson(object);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(result);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
