package com.example.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class GraphqlScalarConfig {
	 @Bean
	    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
	        return wiringBuilder -> wiringBuilder
	            .scalar(ExtendedScalars.Json)         // JSON scalar
	            .scalar(ExtendedScalars.Object);      // JSONObject scalar
	          //  .scalar(ExtendedScars.);   // optional
	    }
}
