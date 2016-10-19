package com.zsx.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping(value="/app")
public class App {
	
	
	@ApiOperation(value="a测试", notes="")
	@RequestMapping(value="/aaa", method=RequestMethod.GET)
	public String hello() {
		return "hello zhao";
	}

	
	
	
	
	
	
	@ApiOperation(value="post测试", notes="提交测试")
	@RequestMapping(value="/bbb", method=RequestMethod.POST)
	public String bbb(@ApiParam(required=true, name="canshu", value="参数") @RequestParam(value="name") String name) {
		return "hello zhao";
	}
	
	
	
	
	@ApiOperation(value="CCC测试", notes="haha")
	@ApiImplicitParam(name="id", value="主键", required=true, dataType="Long")
	@RequestMapping(value="/ccc", method=RequestMethod.GET)
	public String ccc(Long id) {
		return "hello zhao";
	}
	
	
	@ApiOperation(value="D测试", notes="DDDDD")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id", value="主键", required=true, dataType="Long"),
		@ApiImplicitParam(name="name", value="姓名", required=false, dataType="String")
	})
	@RequestMapping(value="/ddd", method=RequestMethod.GET)
	public String ddd(Long id, String name) {
		return "hello zhao";
	}
}
