package com.zsx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/hw")
	public String helloWorld() {
		return "helloWorld";
	}
}
