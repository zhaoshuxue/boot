package com.zsx.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zsx.bean.Demo;
import com.zsx.dao.DemoRepository;

//@Service("demoService")
//@Component
@Service
public class MyDemoService {

	@Autowired
	private DemoRepository demoRepository;

	@Transactional
	public Demo save(Demo demo) {
		Demo demo2 = demoRepository.save(demo);
		return demo2;
	}

}
