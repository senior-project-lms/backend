package com.lms;


import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.User;
import com.lms.enums.AccessLevel;
import com.lms.enums.EPrivilege;
import com.lms.properties.StorageProperties;
import com.lms.repositories.*;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }


    // initially insert the db
    @Autowired
    public void authenticationManager(final AuthorityService authorityService, final PrivilegeService privilegeService, final DefaultAuthorityPrivilegeService defaultAuthorityPrivilegeService, UserService userService) throws Exception {

        authorityService.initialize();
        privilegeService.initialize();
        defaultAuthorityPrivilegeService.initialize();
        userService.initialize();

//
//
//            for (AccessLevel accessLevel : AccessLevel.values()) {
//                Authority role = new Authority();
//                role.generatePublicKey();
//                role.setAccessLevel(accessLevel.CODE);
//                role.setName(accessLevel.toString());
//                authorityRepository.save(role);
//            }
//
//
//            Authority role = authorityRepository.findByAccessLevel(AccessLevel.SUPER_ADMIN.CODE);
//            User user = new User();
//            user.generatePublicKey();
//            user.setUsername("application");
//            user.setEmail("application.com");
//            user.setName("application");
//            user.setSurname("");
//            user.setPassword("test.password");
//            user.setAuthority(role);
//            user.setBlocked(false);
//            user.setEnabled(true);
//            user.setVisible(true);
//            user = customUserDetailService.save(user);
//
//
//            List<com.lms.entities.Privilege> privileges = new ArrayList<>();
//
//            for (EPrivilege EPrivilege : EPrivilege.values()) {
//                com.lms.entities.Privilege privilege1 = new com.lms.entities.Privilege();
//                privilege1.generatePublicKey();
//                privilege1.setCode(EPrivilege.CODE);
//                privilege1.setName(EPrivilege.toString());
//                privilege1.setCreatedBy(user);
//                privilege1 = privilegeRepository.save(privilege1);
//                privileges.add(privilege1);
//            }
//
//
//            role = authorityRepository.findByAccessLevel(AccessLevel.SUPER_ADMIN.CODE);
//            user = new User();
//            user.generatePublicKey();
//            user.setUsername("super.admin");
//            user.setEmail("umit.kas@std.antalya.edu.tr");
//            user.setName("super");
//            user.setSurname("admin");
//            user.setPassword("test.password");
//            user.setAuthority(role);
//            user.setBlocked(false);
//            user.setEnabled(true);
//            user.setVisible(true);
//            user.setAccessPrivileges(privileges);
//            user = customUserDetailService.save(user);
//
//
//            user = new User();
//            user.generatePublicKey();
//            user.setUsername("mock.admin");
//            user.setEmail("umit.kas@outlook.com");
//            user.setName("mock");
//            user.setSurname("admin");
//            user.setPassword("test.password");
//            user.setAuthority(role);
//            user.setBlocked(false);
//            user.setEnabled(true);
//            user.setVisible(true);
//            user = customUserDetailService.save(user);
//
//
//            user = new User();
//            user.generatePublicKey();
//            user.setUsername("mock.lecturer");
//            user.setEmail("mock.lecturer@lms.com");
//            user.setName("mock");
//            user.setSurname("lecturer");
//            user.setPassword("test.password");
//            user.setAuthority(role);
//            user.setBlocked(false);
//            user.setEnabled(true);
//            user.setVisible(true);
//            user = customUserDetailService.save(user);
//
//
//            user = new User();
//            user.generatePublicKey();
//            user.setUsername("mock.student");
//            user.setEmail("mock.student@lms.com");
//            user.setName("mock");
//            user.setSurname("student");
//            user.setPassword("test.password");
//            user.setAuthority(role);
//            user.setBlocked(false);
//            user.setEnabled(true);
//            user.setVisible(true);
//            user = customUserDetailService.save(user);



	}


}
