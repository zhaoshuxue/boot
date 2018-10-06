package com.funimg.core.constant;

/**
 * 全局常量
 * @author zsx
 *
 */
public class Globals {
	
	//点击菜单触发类别
	/**
	 * 无触发
	 */
	public static Integer MENUTYPE_NULL = 0;
	/**
	 * 关键词
	 */
	public static Integer MENUTYPE_KEYWORD = 1;
	/**
	 * 微网站页面
	 */
	public static Integer MENUTYPE_WEBSITE = 2;
	/**
	 * 指定网址
	 */
	public static Integer MENUTYPE_URL = 3;
	
	//菜单的响应动作类型，目前有click、view两种类型
	public static String MENU_TYPE_CLICK = "click";
	public static String MENU_TYPE_VIEW = "view";
	
	//临时二维码和永久二维码
	/**
	 * 临时二维码
	 */
	public static String QR_SCENE = "QR_SCENE";
	/**
	 * 永久二维码
	 */
	public static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
	
	
	//用户的性别
	/**
	 * 值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public static Integer male = 1;
	public static Integer female = 2;
	public static Integer unknown = 0;
	
	//公共号类别
	/**
	 * 服务号 ： 0   订阅号： 1
	 */
	public static Integer FUWUHAO = 0;
	public static Integer DINGYUEHAO = 1;
	
	//自动回复消息类型
	public static String AUTORESP_TEXT = "text";
	public static String AUTORESP_NEWS = "news";
	
	
	//群发消息——发送方式
	/**
	 * 按分组群发 —— o  / 按用户OpenId群发 —— g
	 */
	public static String SEND_BY_GROUP = "g";
	public static String SEND_BY_OPENID = "o";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
