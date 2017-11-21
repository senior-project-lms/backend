package com.lms.configurations;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;

import javax.sql.DataSource;


@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	
	static final String CLIENT_ID = "learningmanagementsystem";
    static final String CLIENT_SECRET = "df05e4ef-08ae-4950-aaec-29dabda5ad62";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String RESOURCES_IDS = "kjkl932nklnkvu9ij33nlf0ijal";
	
    @Autowired
    private DataSource dataSource;
    
 
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    
    
    
    
    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {	

    	endpoints
    		.tokenStore(this.tokenStore())
            .accessTokenConverter(this.jwtTokenEnhancer())
            .authenticationManager(this.authenticationManager)
            .userDetailsService(this.userDetailsService);
    		
    }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
		.withClient(CLIENT_ID)
		.secret(CLIENT_SECRET)
		.authorizedGrantTypes("password", "refresh_token", "access_token")
		.scopes(SCOPE_READ, SCOPE_WRITE)
		.resourceIds(RESOURCES_IDS )
		.accessTokenValiditySeconds(600)
		.refreshTokenValiditySeconds(60 * 60 * 24);
		
	}
	

	
	
   @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
   }


   @Bean
   public TokenStore tokenStore() {
       return new JwtTokenStore(jwtTokenEnhancer());
   }

   @Bean
   protected JwtAccessTokenConverter jwtTokenEnhancer() {
	   JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       return converter;
   }

   @Bean
   protected AuthorizationCodeServices authorizationCodeServices() {
       return new JdbcAuthorizationCodeServices(dataSource);
   }
	
	

}
