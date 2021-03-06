package com.lms;


import com.lms.properties.StorageProperties;
import com.lms.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({StorageProperties.class})
public class App extends SpringBootServletInitializer {

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // run the app
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
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

        try {
            authorityService.initialize();
            privilegeService.initialize();
            defaultAuthorityPrivilegeService.initialize();
            userService.initialize();
        } catch (Exception e) {
            //System.exit(-1);
        }


	}


}
