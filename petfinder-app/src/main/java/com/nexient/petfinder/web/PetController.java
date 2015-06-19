package com.nexient.petfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/pet")
public class PetController {
	
	@RequestMapping("/")
	@ResponseBody
	public String test() {
		
		return "Hello from PetController!";	
	}
	
}
