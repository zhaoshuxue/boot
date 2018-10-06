package com.funimg.web.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduApis {
	
	private static String apikey = "91a4b00d4fd18f745c0a9cdbac58a7cd";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		api_todayInHistory();
		;
		System.out.println(api_hanzizhuanpinyin("拼音我不知道说什么".substring(2)));
		
		
//		
	}
	
	public static String api_qq(String qq){
		
		String httpUrl = "http://apis.baidu.com/3023/qq/qq";
		String httpArg = "uins=" + qq;
		String jsonResult = qq(httpUrl, httpArg);
		
		String str = jsonResult;
		
		// 解析
		StringBuffer returnData = new StringBuffer();
		str = str.replace("portraitCallBack({", "{").replace("})", "}");
		JSONObject obj = JSON.parseObject(str);
		Set<Entry<String, Object>> entrySet = obj.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			returnData.append("QQ号码：").append(entry.getKey()).append("\n");
			String data = entry.getValue().toString();
			data = data.replace("[", "").replace("]", "");
			String[] datas = data.split(",");
			returnData.append("昵称：").append(datas[datas.length - 2].substring(1, datas[datas.length - 2].length() - 1)).append("\n");
			returnData.append("空间头像图片：").append(datas[0].substring(1, datas[0].length() - 1)).append("\n");
		}
		return returnData.toString();
	}
	
	public static String qq(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  apikey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static String api_hanzizhuanpinyin(String data) {
		String httpUrl = "http://apis.baidu.com/sillystudio/service/topy";
		String httpArg = "words=" + URLEncoder.encode(data) + "&accent=0&traditional=0&letter=0&oc=0&type=json";
		String jsonResult = hanzizhuanpinyin(httpUrl, httpArg);
		System.out.println(jsonResult);
		JSONObject obj = JSON.parseObject(jsonResult);
		if ("200".equals(obj.get("code").toString())) {
			return obj.getString("py");
		}else {
			return "转换错误！";
		}
	}
	
	public static String hanzizhuanpinyin(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  apikey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	
	
	
	
	
	public static String api_todayInHistory() {
		String yue = String.valueOf(new Date().getMonth() + 1);
		String ri = String.valueOf(new Date().getDay());
		
		String httpUrl = "http://apis.baidu.com/avatardata/historytoday/lookup";
		String httpArg = "yue=" + yue + "&ri=" + ri + "&type=1&page=1&rows=20&dtype=JOSN&format=false";
		String jsonResult = todayInHistory(httpUrl, httpArg);

		StringBuffer returnData = new StringBuffer();
		returnData.append(yue).append("月").append(ri).append("号")
			.append("历史上发生的大事有：").append("\n");

		JSONObject obj = JSON.parseObject(jsonResult);
		JSONArray array = obj.getJSONArray("result");
		for (int i = 0; i < array.size(); i++) {
			returnData.append(array.getJSONObject(i).get("year"))
				.append("   ")
				.append(array.getJSONObject(i).get("title"))
				.append("\n");
		}
		return returnData.toString();
	}
	
	
	
	
	
	public static String todayInHistory(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  apikey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}

