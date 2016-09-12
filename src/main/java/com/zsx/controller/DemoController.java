package com.zsx.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zsx.bean.Demo;
import com.zsx.service.MyDemoService;

@RestController
@RequestMapping("/demo")
@EnableAutoConfiguration
public class DemoController {

//	@Qualifier("demoService")
//	@Autowired
	@Autowired(required=true)
	private MyDemoService myDemoService;
	

	/**
	 * 返回demo数据: 请求地址：http://127.0.0.1:8080/demo/getDemo
	 * 
	 * @return
	 */
	@RequestMapping("/getDemo")
	public Demo getDemo() {
		Demo demo = new Demo();
		demo.setId(1);
		// demo.setName("Angel");
		demo.setName("shuxue");
		return demo;
	}

	@RequestMapping("/save/{name}")
	public String save(@PathVariable String name) {
		Demo d = new Demo();
//		d.setName("Angel");
		d.setName(name);
		myDemoService.save(d);// 保存数据.
		return "ok.Demo2Controller.save";
	}
	
	@RequestMapping("/save2/{name}")
	public Demo save2(@PathVariable String name) {
		Demo d = new Demo();
		d.setName(name);
		Demo demo = myDemoService.save(d);// 保存数据.
		return demo;
	}
}
