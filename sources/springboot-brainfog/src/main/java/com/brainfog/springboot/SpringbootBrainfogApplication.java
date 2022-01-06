package com.brainfog.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootBrainfogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBrainfogApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
	  return "Hello, Spring Boot is up and running";
	}

}
