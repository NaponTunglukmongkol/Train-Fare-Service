package com.trainfee.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class SpringBoot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringBoot.class, args);
	}
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

}
