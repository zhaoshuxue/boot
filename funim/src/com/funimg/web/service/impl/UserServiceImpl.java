package com.funimg.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funimg.web.dao.UserDao;
import com.funimg.web.entity.User;
import com.funimg.web.model.UserModel;
import com.funimg.web.model.json.JsonModel;
import com.funimg.web.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public UserModel login(UserModel user) {
		User reUser = new User();
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", user.getUserName());
//		params.put("passWord", user.getPassWord());

//		Tuser tuser = userDao
//				.get("from Tuser t where t.userName = :userName and t.passWord = :passWord",
//						params);
		
		UserModel tuser = null;
		if (tuser != null) {
			BeanUtils.copyProperties(tuser, reUser);
			return tuser;
		} else {
			return null;
		}

	}
	
	public JsonModel checkUsernameIsExist(String name){
		JsonModel json = new JsonModel();
		User user = userDao.get("from User t where t.userName = '" + name.trim() + "'");
		if (user == null) {
			json.setSuccess(true);
			json.setMsg("用户名不存在");
		}else {
			json.setSuccess(false);
			json.setMsg("用户名已经存在");
		}
		return json;
	}



}
