package com.rliwanag.jpasecurity2;

import com.rliwanag.jpasecurity2.model.User;
import com.rliwanag.jpasecurity2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Jpasecurity2Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpasecurity2Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
		return args -> {
			users.save(new User("user",encoder.encode("password"),"ROLE_USER", "user", "user"));
			users.save(new User("admin",encoder.encode("password"),"ROLE_USER,ROLE_ADMIN", "admin", "admin"));
		};
	}

}
