package com.itesm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class DemoApplication extends ResourceServerConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

// @SpringBootApplication
// public class DemoApplication {
// 	public static void main(String[] args) {
// 		SpringApplication.run(DemoApplication.class, args);
// 	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
	}
}
