package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

public class Upload {


	public static void main(String[] args) throws Exception {
		// 从业务服务端得到上传凭证
//		String uptoken = "<Get it from the biz server!>";
		String uptoken = getUptoken();
//		String uptoken = "Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ:RRDktgGeZx8J3JvBu6kMsjL8Q0E=:eyJzY29wZSI6ImhpZ2huZXNzIiwiZGVhZGxpbmUiOjE0MTE5ODA5NTd9";
		
		/*
		// 注：此处的key可以类比数据中表中的主键，此处用上传文件的文件名。
		String key = "QQ图片20140820175225.gif";
		String dir = System.getProperty("user.home");
		
		// 本地文件的绝对路径
		String localFile = dir + "/Desktop/" + key;

		// 可选的上传选项，具体说明请参见使用手册。
		PutExtra extra = new PutExtra();
		
		// 上传文件
		PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
		
		if (ret.ok()) {
			System.out.println("Successfully upload the file.");
		} else {
			System.out.println("opps, error : " + ret);
			return;
		}
		*/
		/**
		 * 第二种方法
		 */
		File file = new File("E:/Desktop/460.gif");
		
		InputStream in = new FileInputStream(file);		
		
		String key = file.getName();
		PutExtra extra = new PutExtra();
		PutRet pr = IoApi.Put(uptoken, "LOVE.gif", in, extra);
		System.out.println(pr.response);
		System.out.println(pr.statusCode);
		System.out.println(pr.ok());

		/*
{"hash":"Fnun6-zd-nvDL327ugBKtTTtL-HF","key":"2d951c001437b7fb267fb511.gif"}
200
2d951c001437b7fb267fb511.gif
{"hash":"Fnun6-zd-nvDL327ugBKtTTtL-HF","key":"2d951c001437b7fb267fb511.gif"}
200
true
{"hash":"Fnun6-zd-nvDL327ugBKtTTtL-HF","key":"2d951c001437b7fb267fb511.gif"}


{"hash":"Fgd38JvdSJu2ObMKMf2GMv-3Z7en","key":"idocdown_v309_setup.rar"}
200
idocdown_v309_setup.rar
{"hash":"Fgd38JvdSJu2ObMKMf2GMv-3Z7en","key":"idocdown_v309_setup.rar"}
200
true
{"hash":"Fgd38JvdSJu2ObMKMf2GMv-3Z7en","key":"idocdown_v309_setup.rar"}
		 */
		
	}
	
	public static String getUptoken() throws AuthException, JSONException{
		//634790417@qq.com
		Config.ACCESS_KEY = "Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ";
		Config.SECRET_KEY = "LeLKIY2qHsZgklLTxlGzk0kaoazTYkAn1K7hxKe4";
		String bucketName = "highness";
		
		//zhaoshuxueblog@sina.com
//		Config.ACCESS_KEY = "5ptAUPNb2Dm9YqDm-MXVp5zo0LEGom_P8J6cYWuB";
//		Config.SECRET_KEY = "8Kzrp1qXwQfyXh9itXof-PhJbP7Vr3szt6c3sdeH";
//		String bucketName = "highnesss";
		
		//zhaoshuxuesohu@sohu.com
//		Config.ACCESS_KEY = "xX8Um1HxRKht01VGnZK5aBEjoVzxseSbQzeuY7yc";
//		Config.SECRET_KEY = "uim_phxxziRZz6X5cMjNxcoX6B7sLDsKIKKafDl7";
//		String bucketName = "highnessss";
		//
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		System.out.println("uptoken :  " + uptoken);
		//Z5DLmsX7sYiySxDgtk-QEtyqCFjv1tLYVgEIMoBQ:RRDktgGeZx8J3JvBu6kMsjL8Q0E=:eyJzY29wZSI6ImhpZ2huZXNzIiwiZGVhZGxpbmUiOjE0MTE5ODA5NTd9
		return uptoken;
	}

}
