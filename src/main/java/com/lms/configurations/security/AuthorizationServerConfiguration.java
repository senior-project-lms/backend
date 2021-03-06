package com.lms.configurations.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @Value("#{'${security.jwt.grant-type}'.split(', ')}")
    private String[] grantType;

    @Value("${security.jwt.scope-read}")
    private String scopeRead;

    @Value("${security.jwt.scope-write}")
    private String scopeWrite = "write";

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
	

 
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    
    
    
    
    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtTokenEnhancer());

//    	endpoints
//    		.tokenStore(this.tokenStore())
//            .accessTokenConverter(this.jwtTokenEnhancer())
//            .authenticationManager(this.authenticationManager)
//            .userDetailsService(this.userDetailsService);
    		
    }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
		.withClient(clientId)
		.secret(clientSecret)
		.authorizedGrantTypes(grantType)
		.scopes(scopeRead, scopeWrite)
		.resourceIds(resourceIds )
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

//   @Bean
//   protected AuthorizationCodeServices authorizationCodeServices() {
//       return new JdbcAuthorizationCodeServices(dataSource);
//   }
//
	

}
