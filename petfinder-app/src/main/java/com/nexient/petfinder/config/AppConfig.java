package com.nexient.petfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 
@EnableWebMvc
@Configuration
@ComponentScan({ "com.nexient.petfinder.web.*" })
@Import({ SecurityConfig.class })
public class AppConfig {
 
	// Beans go here.
 
}