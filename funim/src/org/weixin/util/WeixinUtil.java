package org.weixin.util;

import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ConnectException;  
import java.net.URL;  
import java.util.List;
  
import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  

import org.weixin.pojo.AccessToken;
import org.weixin.pojo.Menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
  
//import net.sf.json.JSONObject;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * 公众平台通用接口工具类 
 */  
public class WeixinUtil {  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
  
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
//            jsonObject = JSONObject.fromObject(buffer.toString());  
            jsonObject = JSON.parseObject(buffer.toString());
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
    
    
    
 // 获取access_token的接口地址（GET） 限200（次/天）  
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
      
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
      
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }  
    
 // 菜单创建（POST） 限100（次/天）  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
      									  
    /** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(Menu menu, String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSON.toJSONString(menu);  
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInteger("errcode")) {  
                result = jsonObject.getInteger("errcode");  
//                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }  
    
    //查询菜单(get)  限10000（次/天）
    public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    
    public static JSONObject getMenus(String accessToken){
    	
    	String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
    	
    	JSONObject jsonObject = httpRequest(url, "GET", null);
    	System.out.println(jsonObject);
    	
    	return jsonObject;
    }
    
    //创建二维码ticket
    public static String create_qrcode_ticket = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    
    public static String createQrcodeTicket(String accessToken, String sceneId, boolean actionName){
    	String url = create_qrcode_ticket.replace("ACCESS_TOKEN", accessToken);
    	
    	if (actionName) {
    		String jsonString = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId + "}}}";
    		JSONObject jsonObject = httpRequest(url, "POST", jsonString);
    		System.out.println(jsonObject);
    		if (null != jsonObject) {
				return jsonObject.getString("ticket");
			}
		}else {
			String jsonString = "{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId + "}}}";
			JSONObject jsonObject = httpRequest(url, "POST", jsonString);
			System.out.println(jsonObject);
    		if (null != jsonObject) {
				return jsonObject.getString("ticket");
			}
		}
    	return "";
    }
    
    //通过ticket换取二维码  100000
    public static String get_qrcode = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
    
    //获取用户基本信息
    public static String get_user_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    
    public static JSONObject getUserinfo(String accessToken, String openId){
    	
    	String url = get_user_info.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
    	
    	JSONObject jsonObject = httpRequest(url, "GET", null);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //查询所有分组 1000次每天 GET
    public static String get_group = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
    /**
     * 查询所有分组
     */
    public static JSONObject getGroups(String accessToken){
    	String url = get_group.replace("ACCESS_TOKEN", accessToken); 
    	JSONObject jsonObject = httpRequest(url, "GET", null);
    	System.out.println(jsonObject);
    	return jsonObject;
    	
    }
    
    //创建分组  1000次每天       最多支持创建500个分组      POST数据{"group":{"name":"test"}}
    public static String create_group = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
    /**
     * 创建分组
     * @param accessToken
     * @param groupName
     * @return  正常返回   {"group": {"id": 107,"name": "test"}}
     */
    public static JSONObject createGroup(String accessToken, String groupName){
    	String url = create_group.replace("ACCESS_TOKEN", accessToken); 
    	String json = "{\"group\":{\"name\":\"" + groupName + "\"}}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //查询用户所在分组
    public static String get_group_by_openid = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
    /**
     * 查询用户所在分组
     * @param args
     */
    public static JSONObject getGroupId(String accessToken, String openid){
    	String url = get_group_by_openid.replace("ACCESS_TOKEN", accessToken); 
    	String json = "{\"openid\":\"" + openid + "\"}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //修改分组名
    public static String update_group = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
    /**
     * 修改分组名
     * @param args
     */
    public static JSONObject updateGroup(String accessToken, String groupId, String name){
    	String url = update_group.replace("ACCESS_TOKEN", accessToken); 
    	String json = "{\"group\":{\"id\":" + groupId + ",\"name\":\"" + name + "\"}}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //移动用户分组
    public static String update_group_member = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
    /**
     * 移动用户分组
     * @param args
     */
    public static JSONObject updateGroupMember(String accessToken, String openid, String to_groupid){
    	String url = update_group_member.replace("ACCESS_TOKEN", accessToken); 
    	String json = "{\"openid\":\"" + openid + "\",\"to_groupid\":" + to_groupid + "}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //上传图文消息素材
    public static String upload_news = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    /**
     * 上传图文消息素材
     * return 
     * {
   		"type":"news",
   		"media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ",
   		"created_at":1391857799
		}
     */
    public static JSONObject uploadNews(String accessToken, String json){
    	String url = upload_news.replace("ACCESS_TOKEN", accessToken);
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //根据分组进行群发 post
    public static String sendall = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    /**
     * 根据分组进行群发 --- 图文消息
     * {"filter":{"group_id":"2"},"mpnews":{"media_id":"123dsdajkasd231jhksad"},"msgtype":"mpnews"}
     * return 
     * {
		   "errcode":0,
		   "errmsg":"send job submission success",
		   "msg_id":34182
		}
     */
    public static JSONObject sendallByGroup(String accessToken, String groupId, String mediaId ){
    	String url = sendall.replace("ACCESS_TOKEN", accessToken);
    	String json = "{\"filter\":{\"group_id\":\"" + groupId + "\"},\"mpnews\":{\"media_id\":\"" + mediaId + "\"},\"msgtype\":\"mpnews\"}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //根据OpenID列表群发  POST 图文消息
    public static String send = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    /**
     * 根据OpenID列表群发 图文消息
     * post {"touser":["OPENID1","OPENID2"],"mpnews":{"media_id":"123dsdajkasd231jhksad"},"msgtype":"mpnews"}
     * return {
				   "errcode":0,
				   "errmsg":"send job submission success",
				   "msg_id":34182
				}
     */
    public static JSONObject sendByOpenID(String accessToken, List<String> openIdList, String mediaId ){
    	String url = send.replace("ACCESS_TOKEN", accessToken);
    	
    	String opendidjson = JSON.toJSONString(openIdList);
    	String json = "{\"touser\":" + opendidjson + ",\"mpnews\":{\"media_id\":\"" + mediaId + "\"},\"msgtype\":\"mpnews\"}";
    	
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    //删除群发 POST
    public static String delete_mass = "https://api.weixin.qq.com//cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
    /**
     * 根据msgid删除群发 
     * post {"msgid":30124}
     * return {
				   "errcode":0,
				   "errmsg":"ok"
				}
     */
    public static JSONObject deleteMass(String accessToken, Integer msgid ){
    	String url = delete_mass.replace("ACCESS_TOKEN", accessToken);
    	
    	String json = "{\"msgid\":" + msgid + "}";
    	JSONObject jsonObject = httpRequest(url, "POST", json);
    	System.out.println(jsonObject);
    	return jsonObject;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //***************************************
    //***************************************
    public static void main(String[] args) {
    	/**
    	 * 创建分组test
    	 */
		//createGroup("v2t5zY7r9Fx-IJJivhZXZU6NZIBINr4iLFTArMwoLIlBwzCDOnoltpCKLAcMLTapMz0TCjyD32mNIxIFxlKTYA", "分组一");
		//{"group":{"id":100,"name":"分组一"}}
    	 /**
         * 查询所有分组test
         */
    	//getGroups("v2t5zY7r9Fx-IJJivhZXZU6NZIBINr4iLFTArMwoLIlBwzCDOnoltpCKLAcMLTapMz0TCjyD32mNIxIFxlKTYA");
    	//{"groups":[{"count":5,"id":0,"name":"未分组"},{"count":0,"id":1,"name":"黑名单"},{"count":0,"id":2,"name":"星标组"},{"count":0,"id":100,"name":"分组一"}]}
    	/**
    	 * 查询用户所在分组test
    	 */
    	//getGroupId("v2t5zY7r9Fx-IJJivhZXZU6NZIBINr4iLFTArMwoLIlBwzCDOnoltpCKLAcMLTapMz0TCjyD32mNIxIFxlKTYA", "o12t0t_TYxQQ2mRxqf1vgGzOMQZk");
    	//{"groupid":0}
    	/**
    	 * 修改分组名test
    	 */
    	//updateGroup("v2t5zY7r9Fx-IJJivhZXZU6NZIBINr4iLFTArMwoLIlBwzCDOnoltpCKLAcMLTapMz0TCjyD32mNIxIFxlKTYA", "100", "修改分组名");
    	//{"errcode":0,"errmsg":"ok"}
    	/**
    	 * 移动用户分组test
    	 */
    	//updateGroupMember("v2t5zY7r9Fx-IJJivhZXZU6NZIBINr4iLFTArMwoLIlBwzCDOnoltpCKLAcMLTapMz0TCjyD32mNIxIFxlKTYA", "o12t0t_TYxQQ2mRxqf1vgGzOMQZk", "100");
    	//{"errcode":0,"errmsg":"ok"}
    	
    	
    }
}  