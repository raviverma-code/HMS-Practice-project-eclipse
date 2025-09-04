package com.AuthService.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.AuthService.service.CustomUserDetailService;
import com.AuthService.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService ;
	
	@Autowired
	private CustomUserDetailService userDetailService ;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authToken = request.getHeader("Authorization");
		
		if(authToken != null && authToken.startsWith("Bearer")) {
			String jwtToken = authToken.substring(7);
			String userName = jwtService.ValidateTokenReturnUsername(jwtToken);
			
			if(userName!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userByUsername = userDetailService.loadUserByUsername(userName);
				
				var securityData = new UsernamePasswordAuthenticationToken(userByUsername, null , userByUsername.getAuthorities());
				    securityData.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				SecurityContextHolder.getContext().setAuthentication(securityData);
				System.out.println(">>> Custom Filter Triggered: " + request.getRequestURI());

			}
		}
		filterChain.doFilter(request, response);
	}

}
