package com.example.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;
import jakarta.annotation.PostConstruct;

@Configuration
public class GraphqlScalarConfig {
	@Bean
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Json) // JSON scalar
				.scalar(ExtendedScalars.UUID); 
	}

	@PostConstruct
	public void initCheck() {
		System.out.println(">>> GraphqlScalarConfig bean HAS BEEN INITIALIZED.");
	}
}
