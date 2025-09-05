package com.microservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice.Jwt.JwtFilter;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	
	@Autowired
	private JwtFilter jwtfilter ;

	String[] publicEndpoints = {
	        "/api/v1/user/register",
	        "/micro1/welcome",
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
				
				
				})
		           .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.csrf().disable().build();
	}


}
