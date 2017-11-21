package com.lms.configurations;


import javax.sql.DataSource;

import com.lms.properties.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	static final String SIGNING_KEY = "kKSMJ92Mknk38njs9HJ8KNALiuc938FH";
	static final int ENCODING_STRENGTH = 256;
	static final String SECURITY_REALM = "LMS";


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;




	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth
		 .userDetailsService(userDetailsService)
         .passwordEncoder(passwordEncoder)
         .and()
         .authenticationProvider(authenticationProvider())
         .jdbcAuthentication()
         .dataSource(dataSource)
         .authoritiesByUsernameQuery("select a.* from authorities a, users u where a.id = u.authority_id and u.username = ?");
    }



	@Override
	protected void configure(HttpSecurity http) throws Exception {

        http
        .requestMatchers()
        .antMatchers(HttpMethod.OPTIONS, "/**" )
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**", "/oauth/token").permitAll()
		.antMatchers("/api/**").authenticated()

				.antMatchers("/api/admin/**").hasRole(Integer.toString(AccessLevel.SUPER_ADMIN.CODE))
		.and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .realmName(SECURITY_REALM);



	}



	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}

	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
