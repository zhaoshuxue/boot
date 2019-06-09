package com.weixin.util;


import java.io.*;
import java.net.*;

public class MoLi {


    public static String reboot(String str) throws Exception {
        String ApiKey = "";
        String ApiSecret = "";

        String INFO = URLEncoder.encode(str, "utf-8");
        String getURL = "http://i.itpk.cn/api.php?question="+str+"&api_key="+ApiKey+"&api_secret=" + ApiSecret;
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




    public static void main(String[] args) {

        try {
            String res = reboot("天气预报");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
