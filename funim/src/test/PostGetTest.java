package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class PostGetTest {

	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

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

		} catch (Exception e) {
		}
		return buffer.toString();
	}

	/**
	 * utf编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 替换邀请码
	 */
	public static String translate(String source) {
		String dst = null;

		// 组装查询地址
		String requestUrl = "http://gfw74.tk/register.php?action=reginvcodeck&reginvcode={keyWord}";
		// 对参数q的值进行urlEncode utf-8编码
		requestUrl = requestUrl.replace("{keyWord}", urlEncodeUTF8(source));

		// 查询并解析结果
		try {
			// 查询并获取返回结果
			String json = httpRequest(requestUrl);
			//
			dst = json;
//			 通过Gson工具将json转换成TranslateResult对象
//			TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
//			 取出translateResult中的译文
//			dst = translateResult.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == dst)
			dst = "翻译系统异常，请稍候尝试！";
		return dst;
	}
	
	


	public static void main(String[] args) {
		//
		
		for (int i = 0; i < 50; i++) {
			String ma = getYaoQingMa();
			String res = translate(ma);
			System.out.println(i+1 + " <:> " + ma + " <:> " + res);
		}
		//d4f4cf5c3eece57d
		//c5d111ad4b489bec
		
	}
	
	public static String getYaoQingMa(){
		StringBuilder sb = new StringBuilder();
		String[] data = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
		//16
		Random random = new Random();
		
		for (int i = 0; i < 16; i++) {
//			int num = random.nextInt(16);
//			String str = data[num];
//			sb.append(str);
			sb.append(data[random.nextInt(16)]);
		}
		
//		System.out.println(sb.toString());
		return sb.toString();
	}


}
