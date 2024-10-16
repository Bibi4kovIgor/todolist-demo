package com.todolistservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan("com.todolistservice.config")
public class SecurityConfig {

	private final CustomAuthenticationProvider authProvider;

	public SecurityConfig(CustomAuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated())
				.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(authProvider);
		return http.build();
	}}
