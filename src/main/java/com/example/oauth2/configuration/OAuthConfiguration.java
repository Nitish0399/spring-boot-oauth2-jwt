package com.example.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${app.clientId}")
	private String clientId;

	@Value("${app.clientPassword}")
	private String clientPassword;

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient(clientId).secret(passwordEncoder.encode(clientPassword))
		.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials").scopes("read","write")
		.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER","ADMIN")
		.autoApprove(true)
		.accessTokenValiditySeconds(86400)//Access token is only valid for 1 day.
        .refreshTokenValiditySeconds(3600);//Refresh token is only valid for 60 minutes.;
	}

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(defaultAccessTokenConverter())
    	.userDetailsService(userDetailsService);
    }

	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(defaultAccessTokenConverter());	
	}

	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtSecret);
		return converter;
	}
}
