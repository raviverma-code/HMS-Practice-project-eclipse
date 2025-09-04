package com.AuthService.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AuthService.entity.User;
import com.AuthService.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository ;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userDetails = userRepository.findByUsername(username);
		
		//Collection<SimpleGrantedAuthority> role = Collections.singleton(new SimpleGrantedAuthority(userDetails.getRole()));
		
		return new  org.springframework.security.core.userdetails.User(userDetails.getUsername() ,userDetails.getPassword() , Collections.singleton(new SimpleGrantedAuthority(userDetails.getRole())));
	}

}
