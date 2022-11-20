package com.project.dinopedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class  DinopediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinopediaApplication.class, args);
	}

}
