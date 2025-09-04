package com.AuthService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminPortal {

	@GetMapping("/welcome")
	public String welcomePage() {
		return "Welcome Admin Sir " ;
	}
}
