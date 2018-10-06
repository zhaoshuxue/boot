package com.funimg.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
* @ClassName: ContextHolderUtils 
* @Description: TODO(上下文工具类) 
*
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 可以在普通类中使用request
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;

	}
	
	/**
	 * SpringMvc下获取response
	 * 可以在普通类中使用response
	 * @return
	 * 会报如下错误java.lang.ClassCastException: org.springframework.web.context.request.ServletRequestAttributes cannot be cast to org.springframework.web.context.request.ServletWebRequest
	 */
//	public static HttpServletResponse getResponse() {
//		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
//		return response;
//
//	}
	
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
	/**
	 * 获取项目根地址
	 * @return
	 */
	public static String getBasePath(){
		HttpServletRequest request = getRequest();
		//获取项目真实路径 
        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://"
//               + request.getServerName() + ":" + request.getServerPort()
//               + path + "/";
        //返回的路径为 http://funimg.duapp.com:80/ ， 不需要有端口信息，所以去掉getServerPort()
        String basePath = request.getScheme() + "://"
        + request.getServerName() + path + "/";
        return basePath;
	}

}
