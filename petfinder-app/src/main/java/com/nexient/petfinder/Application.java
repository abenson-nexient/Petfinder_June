package com.nexient.petfinder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nexient.petfinder.config.AppConfig;

public class Application {
	public static void main(String[] args) {
		System.out.println("Application has been called");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
	}
}
