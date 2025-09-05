package com.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.dto.UserDto;


@FeignClient(name="AUTHSERVICEAPP", url = "http://localhost:9091")
public interface AuthServiceFeignClient {

	@GetMapping("/api/v1/user/get-user")
	UserDto getUserDetails(@RequestParam("username") String username , @RequestHeader("Authorization") String token);
}
