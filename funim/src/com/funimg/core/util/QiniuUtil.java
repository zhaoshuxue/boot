package com.funimg.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

public class QiniuUtil {
	/**
	 * 上传图片
	 */
	public static boolean uploadImageToQiniu(String uptoken, InputStream is, String key) throws AuthException, JSONException, FileNotFoundException{
		PutExtra extra = new PutExtra();
		PutRet pr = IoApi.Put(uptoken, key, is, extra);
		System.out.println(pr.response);
		System.out.println(pr.statusCode);
		System.out.println(pr.ok());
		return pr.ok();
	}
	
	/**
	 * 获取uptoken
	 */
	public static String getUptoken() throws AuthException, JSONException{
		//634790417@qq.com
//		Config.ACCESS_KEY = "Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ";
//		Config.SECRET_KEY = "LeLKIY2qHsZgklLTxlGzk0kaoazTYkAn1K7hxKe4";
//		String bucketName = "highness";
		
		//596886465@qq.com
//		Config.ACCESS_KEY = "wBDsTDSek4zBFXQjOZDk5TTGzLTL4phm2f-ClQc3";
//		Config.SECRET_KEY = "VJF0SsV3LdovvgWD8KXMGMAt40ETkHeFfYNDXEeX";
//		String bucketName = "funimg";
		
		//zhaoshuxueblog@sina.com
//		Config.ACCESS_KEY = "5ptAUPNb2Dm9YqDm-MXVp5zo0LEGom_P8J6cYWuB";
//		Config.SECRET_KEY = "8Kzrp1qXwQfyXh9itXof-PhJbP7Vr3szt6c3sdeH";
//		String bucketName = "highnesss";
		
		//zhaoshuxuesohu@sohu.com
		Config.ACCESS_KEY = "xX8Um1HxRKht01VGnZK5aBEjoVzxseSbQzeuY7yc";
		Config.SECRET_KEY = "uim_phxxziRZz6X5cMjNxcoX6B7sLDsKIKKafDl7";
		String bucketName = "highnessss";
		//
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
//		System.out.println("uptoken :  " + uptoken);
		//
		return uptoken;
	}
}
