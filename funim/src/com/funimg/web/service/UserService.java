package com.funimg.web.service;


import com.funimg.web.model.UserModel;
import com.funimg.web.model.json.JsonModel;
/**
 * 用户管理service接口
 * @author zsx
 *
 */
public interface UserService {
	/**
	 * 用户登录
	 */
	UserModel login(UserModel user);
	/**
	 * 注册时检查用户名是否已经存在
	 */
	JsonModel checkUsernameIsExist(String name);
	
}
