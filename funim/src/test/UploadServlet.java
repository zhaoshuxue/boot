package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		if (in.available() == 0) {
			return;
		}

		String uptoken = null;
		uptoken = "Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ:lG0DfK0OFGwucClpNU5D7eG5An4=:eyJzY29wZSI6ImhpZ2huZXNzIiwiZGVhZGxpbmUiOjE0MTE5ODc1MzJ9";
//		try {
//			uptoken = getUptoken();
//		} catch (AuthException | JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String key = UUID.randomUUID().toString() + ".gif";
		PutExtra extra = new PutExtra();
		PutRet pr = IoApi.Put(uptoken, key, in, extra);
		System.out.println(pr.response);
		System.out.println(pr.statusCode);
		System.out.println(pr.ok());

		in.close();

	}

	public static String getUptoken() throws AuthException, JSONException {
		// 634790417@qq.com
		Config.ACCESS_KEY = "Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ";
		Config.SECRET_KEY = "LeLKIY2qHsZgklLTxlGzk0kaoazTYkAn1K7hxKe4";
		String bucketName = "highness";

		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		System.out.println("uptoken :  " + uptoken);
		return uptoken;
	}

}
