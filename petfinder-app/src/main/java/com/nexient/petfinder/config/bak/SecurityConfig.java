package com.nexient.petfinder.config.bak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ian").password("bob").roles("USER");
		auth.inMemoryAuthentication().withUser("daniel").password("pizza").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/auth/admin").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/auth/user").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')").and()
			.formLogin();
	}
}