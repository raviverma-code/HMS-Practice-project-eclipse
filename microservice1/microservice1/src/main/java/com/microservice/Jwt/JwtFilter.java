package com.microservice.Jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microservice.client.AuthServiceFeignClient;
import com.microservice.dto.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService ;
	
	@Autowired
	private AuthServiceFeignClient authServiceFeignClient;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authToken = request.getHeader("Authorization");
		
		if(authToken != null && authToken.startsWith("Bearer")) {
			String jwtToken = authToken.substring(7);
			String userName = jwtService.ValidateTokenReturnUsername(jwtToken);
			
			if(userName!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
				 UserDto  userByUsername =authServiceFeignClient.getUserDetails(userName, authToken);
				
				var securityData = new UsernamePasswordAuthenticationToken(userByUsername, null ,Collections.singleton(new SimpleGrantedAuthority(userByUsername.getRole())));
				    securityData.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				SecurityContextHolder.getContext().setAuthentication(securityData);

			}
		}
		filterChain.doFilter(request, response);
	}

}
