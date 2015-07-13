package com.nexient.petfinder.config.bak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.nexient.petfinder.web","com.systemsinmotion.petrescue.web"})
/*@ComponentScan(basePackageClasses = {}
		 com.nexient.petfinder.Application.class,
		com.systemsinmotion.petrescue.web.PetFinderConsumer.class, com.systemsinmotion.petrescue.entity.Entity.class,
		org.petfinder.entity.Petfinder.class, com.nexient.petfinder.web.PetController.class })
*/
public class RootConfig {

	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
