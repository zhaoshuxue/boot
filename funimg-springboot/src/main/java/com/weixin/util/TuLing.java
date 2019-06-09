package com.weixin.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
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
    public static void main1(String[] args) throws IOException {

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
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
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

    public static void main(String[] args) {
        String url = "http://openapi.tuling123.com/openapi/api/v2";

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", "da3d9c26d3bc4d8987cc1e1f8ef5e996");
        userInfo.put("userId", "test");


        JSONObject inputText = new JSONObject();
        inputText.put("text", "你是谁");

        JSONObject perception = new JSONObject();
        perception.put("inputText", inputText);

        JSONObject params = new JSONObject();
        params.put("reqType", 0);
        params.put("userInfo", userInfo);
        params.put("perception", perception);


        String s = loadData(url, params.toString());
        System.out.println(s);


    }


    public static String rebootV2(String str) throws Exception {
        String url = "http://openapi.tuling123.com/openapi/api/v2";

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", "da3d9c26d3bc4d8987cc1e1f8ef5e996");
        userInfo.put("userId", "test");


        JSONObject inputText = new JSONObject();
        inputText.put("text", str);

        JSONObject perception = new JSONObject();
        perception.put("inputText", inputText);

        JSONObject params = new JSONObject();
        params.put("reqType", 0);
        params.put("userInfo", userInfo);
        params.put("perception", perception);


        String res = loadData(url, params.toString());

        JSONObject object = JSONObject.parseObject(res);
        JSONArray results = object.getJSONArray("results");
        if (results != null && results.size() > 0){
            JSONObject jsonObject = results.getJSONObject(0);
            String resultType = jsonObject.getString("resultType");
            if (resultType != null && "text".equals(resultType)){
                JSONObject values = jsonObject.getJSONObject("values");
                return values.getString("text");
            }
        }
        return "解析错误!!!";
    }



    public static String loadData(String url, String params) {
        try {
            URL wsUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes(Charset.forName("UTF-8")));

            InputStream is = conn.getInputStream();

            byte[] b = new byte[1024];
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            while ((len = is.read(b)) != -1) {
                String ss = new String(b, 0, len, "UTF-8");
                buffer.append(ss);
            }
            is.close();
            os.close();
            conn.disconnect();

            String resultStr = buffer.toString();
            System.out.println(resultStr);
            return resultStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
