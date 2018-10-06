package com.funimg.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具 MD5
 */
public class MD5Util {

	public static String md5(String str) {
		try {
			//根据MD5算法生成MessageDigest对象  
			MessageDigest md = MessageDigest.getInstance("MD5");//同样适用于SHA
			//使用srcBytes更新摘要  
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加加密结果
			return buf.toString();
			// 16位加密结果
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
