package com.lms;


import com.lms.entities.User;
import com.lms.properties.AccessLevel;

import com.lms.entities.Authority;
import com.lms.repositories.AuthorityRepository;
import com.lms.repositories.UserRepository;

import com.lms.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class App {

	@Bean
	BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	// run the app
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}



	// if there is no user initially creates a user
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder, final UserRepository userRepository, UserService userService, final AuthorityRepository authorityRepository) throws Exception {
		if (userRepository.count() == 0) {

			Authority role = new Authority();
			role.setAcessLevel(AccessLevel.SUPER_ADMIN.CODE);
			role.setName(AccessLevel.SUPER_ADMIN.toString());
			authorityRepository.save(role);

			User user = new User();
			user.setUsername("super.admin");
			user.setName("super");
			user.setSurname("admin");
			user.setPassword("123");
			user.setAuthority(role);
			user.setBlocked(false);
			user.setEnabled(true);
			userService.save(user);

		}
	}

}
