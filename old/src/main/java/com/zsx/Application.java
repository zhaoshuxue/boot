package com.zsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {
	
	@RequestMapping("/nihao")
	public String nihao(){
		return "我很好";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

