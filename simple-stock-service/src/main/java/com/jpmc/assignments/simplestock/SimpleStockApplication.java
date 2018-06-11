package com.jpmc.assignments.simplestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Backbone of Simple Stock application. This is the main
 * class that would be loading the whole application up
 * 
 * @author jnair1
 *
 */
@SpringBootApplication
public class SimpleStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleStockApplication.class, args);
	}

}
