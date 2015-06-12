package com.nexient.petfinder.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/auth"})
public class AuthController {
	@RequestMapping("/resource")
	public Map<String,Object> home() {
		 Map<String,Object> model = new HashMap<String,Object>();
		 model.put("id", UUID.randomUUID().toString());
		 model.put("content", "Hello World");
		 return model;
	}
	 
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		 response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
