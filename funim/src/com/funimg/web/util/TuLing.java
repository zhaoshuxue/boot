package com.funimg.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class TuLing {
	
	
	public static String reboot(String str) throws Exception {
		String APIKEY = "a8fdcab49e44f87a789b599d19e11e4b"; //"开发者注册帐号，激活之后即可获得";
		String INFO = URLEncoder.encode(str, "utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY
				+ "&info=" + INFO;
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return sb.toString();
	}
	
	
	

	/**
	 * 调用图灵机器人平台接口
	 */
	public static void main(String[] args) throws IOException {

		String APIKEY = "a8fdcab49e44f87a789b599d19e11e4b"; //"开发者注册帐号，激活之后即可获得";
		String INFO = URLEncoder.encode("北京今日天气", "utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY
				+ "&info=" + INFO;
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.connect();

		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println(sb);
		
		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			System.out.println(jsonObject);
			System.out.println(jsonObject.get("code"));
			System.out.println(jsonObject.get("text"));
			
			System.out.println("100000".equals(jsonObject.get("code")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		com.alibaba.fastjson.JSONObject json = JSON.parseObject(sb.toString());
		System.out.println(json);
		System.out.println(json.get("code"));
		System.out.println(json.get("text"));
		
		int i = 100000;
		System.out.println(json.getIntValue("code") == i);

	}

}
