package com.zsx.service;

import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloService {

	public String getName(){
		return "hello";
	}
}
