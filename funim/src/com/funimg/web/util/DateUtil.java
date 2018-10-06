package com.funimg.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期公共方法
 * @author zhao
 *
 */

public class DateUtil {
	/**
	 * 获取当前的时间(格式:yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public static String timeFormat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * 获取当前的日期(格式:yyyy-MM-dd)
	 * @return
	 */
	public static String dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static void main(String[] args) {
		//DateUtil du = new DateUtil();
		//System.out.println(du.timeFormat());

	}

}
