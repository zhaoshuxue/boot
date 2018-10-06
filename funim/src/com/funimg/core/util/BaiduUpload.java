/*package com.funimg.core.util;

import java.io.InputStream;

import org.apache.catalina.Host;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.sun.org.apache.bcel.internal.generic.NEW;

*//**
 * 调用百度api上传图片到百度云存储
 * @author zsx
 *
 *//*
public class BaiduUpload {

	// ----------------------------------------
	static PropertiesUtil pUtil = new PropertiesUtil("baidu.properties");
	
	static String host = pUtil.readProperty("host");
	static String accessKey = pUtil.readProperty("accessKey");
	static String secretKey = pUtil.readProperty("secretKey");
	static String bucket = pUtil.readProperty("bucket");
	
	// ---------------------------------------

	*//**
	 * 上传图片
	 * @param inputStream 文件流
	 * @param folderName 百度文件夹名
	 * @param fileName 文件名
	 * @return fileUrl 文件网络地址
	 *//*

	public static String baiduUploadImage(InputStream inputStream,
			String folderName, String fileName, Boolean acl) {
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8");
		// 拼接文件路径
		String filePath = "/" + folderName + "/" + fileName;
		try {
			putObjectByInputStream(baiduBCS, inputStream, filePath);
			
			if (acl) {
				//公开
				putObjectPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.PublicRead, filePath);
				String fileUrl = generateUrl(baiduBCS, filePath);
				fileUrl = fileUrl.substring(0, fileUrl.indexOf("?sign="));
				System.out.println(fileUrl);
				return fileUrl;
			}else {
				//私有
				putObjectPolicyByX_BS_ACL(baiduBCS, X_BS_ACL.Private, filePath);
				
				String fileUrl = generateUrl(baiduBCS, filePath);
				System.out.println(fileUrl);
				return fileUrl;
			}
		} catch (Exception e) {
		}
		return "";
	}

	*//**
	 * 上传文件流
	 *//*
	public static void putObjectByInputStream(BaiduBCS baiduBCS,
			InputStream inputStream, String filePath) throws Exception {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		objectMetadata.setContentLength(inputStream.available());

		// 参数说明 :  “空间 ” --- 文件夹和文件名 --- 文件流 ------ 记录文件meta信息
		PutObjectRequest request = new PutObjectRequest(bucket, filePath,
				inputStream, objectMetadata);
		baiduBCS.putObject(request).getResult();
		// ObjectMetadata result = baiduBCS.putObject(request).getResult();
	}

	*//**
	 * 生成url
	 *//*
	public static String generateUrl(BaiduBCS baiduBCS, String object) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(
				HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		return baiduBCS.generateUrl(generateUrlRequest);

	}
	
	*//**
	 * 改变文件的访问属性 (公开 / 私有)
	 * @param baiduBCS
	 * @param acl
	 * @param object
	 *//*
	private static void putObjectPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl, String object) {
		//baiduBCS.putBucketPolicy("", X_BS_ACL.PublicRead);
		baiduBCS.putObjectPolicy(bucket, object, acl);
		//公有返回的url依然有sign，但可以截去不要，仍可以访问
		
	}
}
*/