package com.nexient.petfinder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nexient.petfinder.config.WebConfig;

public class Application {
	public static void main(String[] args) {
		System.out.println("Application has been called");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				WebConfig.class);
	}
}
