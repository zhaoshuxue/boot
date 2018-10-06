package com.funimg.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funimg.web.dao.UserDao;
import com.funimg.web.entity.User;
import com.funimg.web.service.InitServiceI;



@Service
@Transactional
public class InitServiceImpl implements InitServiceI {

	@Autowired
	private UserDao userDao;

	@Override
	synchronized public void init() {


		initTuser();// 初始化用户

	}


	private void initTuser() {/*
		User tuser = new User();
//		tuser.setId("0003");
//		tuser.setUserName("xue");
//		tuser.setPassWord("123");
//		tuser.setUserNumber("0003");
////		tuser.setCreateTime(DateUtil.timeFormat());
//		tuser.setIsCancel("0");
//		tuser.setTuser(null);
		userDao.save(tuser);
		
		Tuser tuser1 = new Tuser();
//		tuser1.setId("0002");
//		tuser1.setUserName("shu");
//		tuser1.setPassWord("123");
//		tuser1.setUserNumber("0002");
////		tuser1.setCreateTime(DateUtil.timeFormat());
//		tuser1.setIsCancel("0");
//		tuser1.setTuser(tuser);
		userDao.save(tuser1);
		
		Tuser tuser2 = new Tuser();
		tuser2.setId("0001");
		tuser2.setUserName("zhao");
		tuser2.setPassWord("123");
		tuser2.setUserNumber("0001");
//		tuser2.setCreateTime(DateUtil.timeFormat());
		tuser2.setIsCancel("0");
		tuser2.setTuser(tuser1);
		userDao.save(tuser2);
		
	*/}



	
}
