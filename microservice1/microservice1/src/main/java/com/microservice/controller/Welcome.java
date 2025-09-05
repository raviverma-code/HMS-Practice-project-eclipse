package com.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/micro1")
public class Welcome {
 
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome" ;
	}
}
