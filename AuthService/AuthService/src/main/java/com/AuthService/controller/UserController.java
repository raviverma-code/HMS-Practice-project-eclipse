package com.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthService.dto.ApiResponse;
import com.AuthService.dto.LoginDto;
import com.AuthService.dto.UserDto;
import com.AuthService.entity.User;
import com.AuthService.service.JWTService;
import com.AuthService.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private JWTService jwtService ;
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody UserDto dto) {

		ApiResponse<String> userRegister = userService.userRegister(dto);

		return new ResponseEntity<>(userRegister, HttpStatusCode.valueOf(userRegister.getStatus()));
	}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginDto loginDto) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
				loginDto.getPassword());

		try {
			Authentication authenticate = authManager.authenticate(token);
			if (authenticate.isAuthenticated()) {
				
				String genrateToken = jwtService.genrateToken(loginDto.getUsername(), authenticate.getAuthorities().iterator().next().getAuthority()); //user and role
				
				System.out.print(genrateToken);
				
				ApiResponse<String> response = new ApiResponse<>();
				response.setData("loginMessage");
				response.setMessage("Login successful");
				response.setStatus(202);
				return new ResponseEntity<>(response,  HttpStatusCode.valueOf(response.getStatus()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ApiResponse<String> response = new ApiResponse<>();
		response.setData("loginMessage");
		response.setMessage("Login successful");
		response.setStatus(500);
		return new ResponseEntity<>(response,  HttpStatusCode.valueOf(response.getStatus()));
	}
}
