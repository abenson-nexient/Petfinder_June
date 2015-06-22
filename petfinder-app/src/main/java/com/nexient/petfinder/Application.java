package com.nexient.petfinder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nexient.petfinder.config.WebConfig;

public class Application {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				WebConfig.class);
	}
	
}
