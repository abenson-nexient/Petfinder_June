package com.nexient.petfinder.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.nexient.petfinder.web", "com.systemsinmotion.petrescue.web" })
public class WebConfig {

	/*
	 * @Override public void configureDefaultServletHandling(
	 * DefaultServletHandlerConfigurer configurer) { configurer.enable(); }
	 * 
	 * @Bean public PropertySourcesPlaceholderConfigurer placeholderConfigurer()
	 * { return new PropertySourcesPlaceholderConfigurer(); }
	 */
}
