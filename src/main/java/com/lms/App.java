package com.lms;


import com.lms.entities.*;
import com.lms.properties.AccessLevel;

import com.lms.properties.Privileges;
import com.lms.repositories.*;

import com.lms.services.UserService;
import com.lms.services.custom.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.util.Arrays;
import java.util.UUID;

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


	// initially insert the db
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder, final UserRepository userRepository, final AuthorityRepository authorityRepository, final CourseRepositoy courseRepositoy, final PrivilegeRepository privilegeRepository
	, final UserCoursePrivilegeRepository userCoursePrivilegeRepository, UserService userService) throws Exception {
		if (userRepository.count() == 0) {

			Authority role = new Authority();
			role.generatePublicKey();
			role.setAcessLevel(AccessLevel.SUPER_ADMIN.CODE);
			role.setName(AccessLevel.SUPER_ADMIN.toString());
			authorityRepository.save(role);

			User user = new User();
			user.generatePublicKey();
			user.setUsername("super.admin");
			user.setName("super");
			user.setSurname("admin");
			user.setPassword("123");
			user.setAuthority(role);
			user.setBlocked(false);
			user.setEnabled(true);
			user = userService.save(user);
			//

			Privilege privilege1 = new Privilege();
			privilege1.generatePublicKey();
			privilege1.setCode(UUID.randomUUID().toString());
			privilege1.setName(Privileges.METHOD_X.toString());

			privilege1 = privilegeRepository.save(privilege1);







			Course course = new Course();
			course.generatePublicKey();
			course.setName("Test 1");
			course.setCode("tst 101");
			course.setRegisteredUsers(Arrays.asList(user));
			course.setOwner(user);
			course = courseRepositoy.save(course);


			UserCoursePrivilege userCoursePrivilege = new UserCoursePrivilege();
			userCoursePrivilege.generatePublicKey();
			userCoursePrivilege.setCourse(course);
			userCoursePrivilege.setUser(user);

			userCoursePrivilege.setPrivileges(Arrays.asList(privilege1));

			userCoursePrivilege = userCoursePrivilegeRepository.save(userCoursePrivilege);

			course.setUserCoursePrivileges(Arrays.asList(userCoursePrivilege));

			course = courseRepositoy.save(course);

		}
	}

}
