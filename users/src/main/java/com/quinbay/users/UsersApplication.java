package com.quinbay.users;

import com.quinbay.users.controller.UserController;
import com.quinbay.users.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.quinbay.users.repository")
@EntityScan("com.quinbay.users.model")
@ComponentScan({"com.quinbay.users.service", "com.quinbay.users.controller​", "com.quinbay.users.repository"})

@ComponentScan(basePackageClasses=UserController.class)
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
