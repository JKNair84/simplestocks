package com.jpmc.assignments.simplestock.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author jnair1
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket simpleStockServiceApi() {
		return new Docket(DocumentationType.SWAGGER_2).
				select().
				apis(RequestHandlerSelectors.
				basePackage("com.jpmc.assignments.simplestock.resources")).
				paths(PathSelectors.any()).
				build().
				apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Super Simple Stocks API")
                .description("\"Spring Boot REST API for Super Simple Stock Application\"")
                .version("1.0")
                .contact(new Contact("Jay Nair", "", "nair.jay.k@gmail.com"))
                .build();
    }

}
