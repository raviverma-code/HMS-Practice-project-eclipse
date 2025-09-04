package com.AuthService.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AuthService.dto.ApiResponse;
import com.AuthService.dto.LoginDto;
import com.AuthService.dto.UserDto;
import com.AuthService.entity.User;
import com.AuthService.repository.UserRepository;


@Service
public class UserService {

//    private final AuthService.AuthServiceApplication authServiceApplication;

	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private PasswordEncoder passEncoder ;
	
	@Autowired
	private AuthenticationManager authManager ;

//    UserService(AuthService.AuthServiceApplication authServiceApplication) {
//        this.authServiceApplication = authServiceApplication;
//    }
	
	public ApiResponse<String> userRegister(UserDto dto ){
		
		if(userRepository.existsByUsername(dto.getUsername())) {
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("User Resgister faild");
			response.setStatus(500);
			response.setData("username dublicate ");
			return response ;
		}
       if(userRepository.existsByEmail(dto.getEmail())) {
    	   ApiResponse<String> response = new ApiResponse();
   		response.setMessage("User Resgister Successful");
   		response.setStatus(500);
   		response.setData("email dublicate ");
			return response ;
		}
		
		
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(passEncoder.encode(dto.getPassword()));
		
		userRepository.save(user);
		
		ApiResponse<String> response = new ApiResponse();
		response.setMessage("User Resgister Successful");
		response.setStatus(200);
		response.setData("Successfully Done"); ;
		return response ;
	}

	
	
	
}
