package com.AuthService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.AuthService.filter.JwtFilter;
import com.AuthService.service.CustomUserDetailService;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Autowired
	private CustomUserDetailService userDetailService ;
	
	@Autowired
	private JwtFilter jwtfilter ;

	String[] publicEndpoints = {
	        "/api/v1/user/register",
	        "/api/v1/user/login",
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	        "/swagger-ui.html",
	        "/swagger-resources/**",
	        "/webjars/**"
	    };
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	}
	
	
	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		//http.csrf(csrf->csrf.disable());
		
		http.authorizeHttpRequests(
				auth-> { auth.requestMatchers(publicEndpoints).permitAll()
				.requestMatchers("/api/v1/admin/welcome").hasRole("ADMIN")
				.anyRequest().authenticated() ;
				
				
				}).authenticationProvider(authProvider())
		           .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.csrf().disable().build();
	}
	//return http.build();
//*************************************************** Login Setup****************************************************************S
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config ) throws Exception{
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());           //passwordEncoder beans de do 
		return authProvider ;
	}

}
