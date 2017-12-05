package com.lms;


import com.lms.entities.*;
import com.lms.properties.AccessLevel;

import com.lms.properties.Privileges;
import com.lms.repositories.*;

import com.lms.services.CustomUserDetailService;

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



	// if there is no user initially creates a user
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder, final UserRepository userRepository, final AuthorityRepository authorityRepository, final CourseRepositoy courseRepositoy, final PrivilegeRepository privilegeRepository
	, final UserCoursePrivilegeRepository userCoursePrivilegeRepository, CustomUserDetailService customUserDetailService) throws Exception {
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
			user = customUserDetailService.save(user);
			//

			Privilege privilege1 = new Privilege();
			privilege1.setPublicId(UUID.randomUUID().toString());
			privilege1.setCode(UUID.randomUUID().toString());
			privilege1.setName(Privileges.METHOD_X.toString());

			privilege1 = privilegeRepository.save(privilege1);







			Course course = new Course();
			course.setPublicId(UUID.randomUUID().toString());
			course.setName("Test 1");
			course.setCode("tst 101");
			course.setRegisteredUsers(Arrays.asList(user));
			course = courseRepositoy.save(course);


			UserCoursePrivilege userCoursePrivilege = new UserCoursePrivilege();
			userCoursePrivilege.setCourse(course);
			userCoursePrivilege.setUser(user);

			userCoursePrivilege.setPrivileges(Arrays.asList(privilege1));

			userCoursePrivilege = userCoursePrivilegeRepository.save(userCoursePrivilege);

			course.setUserCoursePrivileges(Arrays.asList(userCoursePrivilege));

			course = courseRepositoy.save(course);

		}
	}

}
