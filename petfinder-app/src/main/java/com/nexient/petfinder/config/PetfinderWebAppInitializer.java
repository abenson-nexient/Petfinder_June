package com.nexient.petfinder.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.nexient.petfinder.config.RootConfig;
import com.nexient.petfinder.config.WebConfig;

public class PetfinderWebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}
	
	@Override
	protected String getServletName() {
		return "Petfinder";
	}
}