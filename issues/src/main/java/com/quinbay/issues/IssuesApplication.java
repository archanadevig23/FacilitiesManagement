package com.quinbay.issues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssuesApplication {
	static int id = 0;

	public static void main(String[] args) {
		SpringApplication.run(IssuesApplication.class, args);
	}

}
