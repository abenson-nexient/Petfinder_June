package com.nexient.petfinder.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping("/")
	public String authHello() {
		return "Hello from AuthController!";
	}
	
	@RequestMapping("/user")
	public String userLogin() {
		return "User login success";
	}
	
	@RequestMapping("/admin")
	public String adminLogin() {
		return "Admin login success";
	}

}