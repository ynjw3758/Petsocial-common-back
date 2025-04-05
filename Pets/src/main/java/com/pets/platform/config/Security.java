package com.pets.platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
public class Security {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 private final String[] allowedUrls = {"/Pets-social","Pets-social/sign","Pets-social/login",
			                               "Pets-social/oauth/kakao"};
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	
	//SecurityFilterChain는 추후에 설정할 예정
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		logger.info("Websecurityconfig start!!");
		
		http.cors().disable()
		.csrf().disable()
		.formLogin().disable()
		.headers().frameOptions().disable();
		
		return http.build();

}
}
