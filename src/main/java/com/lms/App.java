package com.lms;


import com.lms.entities.authority.AccessPrivilege;
import com.lms.entities.authority.Privilege;
import com.lms.entities.authority.Authority;
import com.lms.entities.user.User;
import com.lms.services.interfaces.StorageService;
import com.lms.properties.AccessLevel;

import com.lms.properties.Privileges;

import com.lms.properties.custom.StorageProperties;
import com.lms.repositories.authority.AccessPrivilegeRepository;
import com.lms.repositories.authority.AuthorityRepository;
import com.lms.repositories.authority.PrivilegeRepository;
import com.lms.repositories.course.CourseRepository;
import com.lms.repositories.authority.UserCoursePrivilegeRepository;
import com.lms.repositories.user.UserRepository;
import com.lms.services.custom.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({StorageProperties.class})
public class App {

	@Bean
	BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	// run the app
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}


	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) ->{
			storageService.init();
		};
	}


	// initially insert the db
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder, final UserRepository userRepository, final AuthorityRepository authorityRepository, final CourseRepository courseRepository, final PrivilegeRepository privilegeRepository
	, final UserCoursePrivilegeRepository userCoursePrivilegeRepository, CustomUserDetailService customUserDetailService, final AccessPrivilegeRepository accessPrivilegeRepository) throws Exception {
		if (userRepository.count() == 0) {

			Authority role = new Authority();
			role.generatePublicKey();
			role.setAccessLevel(AccessLevel.SUPER_ADMIN.CODE);
			role.setName(AccessLevel.SUPER_ADMIN.toString());
			authorityRepository.save(role);

			User user = new User();
			user.generatePublicKey();
			user.setUsername("super.admin");
			user.setEmail("umit.kas@std.antalya.edu.tr");
			user.setName("super");
			user.setSurname("admin");
			user.setPassword("test.password");
			user.setAuthority(role);
			user.setBlocked(false);
			user.setEnabled(true);
			user.setVisible(true);
			user = customUserDetailService.save(user);
			//

			AccessPrivilege accessPrivilege = new AccessPrivilege();

			List<Privilege> privileges = new ArrayList<>();

			for(Privileges privilege : Privileges.values()){
				Privilege privilege1 = new Privilege();
				privilege1.generatePublicKey();
				privilege1.setCode(privilege.CODE);
				privilege1.setName(privilege.toString());
				privilege1.setCreatedBy(user);
				privilege1 = privilegeRepository.save(privilege1);
				privileges.add(privilege1);
			}

			accessPrivilege.setPrivileges(privileges);
			accessPrivilege.generatePublicKey();
			accessPrivilege.setVisible(true);
			accessPrivilege.setCreatedBy(user);
			accessPrivilege.setUser(user);
			accessPrivilege = accessPrivilegeRepository.save(accessPrivilege);


			user = new User();
			user.generatePublicKey();
			user.setUsername("mock.admin");
			user.setEmail("umit.kas@outlook.com");
			user.setName("mock");
			user.setSurname("admin");
			user.setPassword("test.password");
			user.setAuthority(role);
			user.setBlocked(false);
			user.setEnabled(true);
			user.setVisible(true);
			user = customUserDetailService.save(user);


		}
	}

}
