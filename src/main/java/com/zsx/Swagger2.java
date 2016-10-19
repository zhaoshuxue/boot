package com.zsx;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import javax.persistence.criteria.Predicate;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

	
	public Docket createRestApi() {
		
	
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zsx.controller"))
//				.paths(PathSelectors.any())
				.paths(or(regex("/app/*")))
				.build();
				
	}
	
	
	
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("更多Spring Boot相关文章请关注")
				.termsOfServiceUrl("http://www.zhaoshuxue.top")
				.contact("赵树学")
				.version("1.0.0")
				.build();
	}
}
