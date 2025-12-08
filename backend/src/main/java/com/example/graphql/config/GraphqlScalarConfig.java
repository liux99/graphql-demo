package com.example.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;
import jakarta.annotation.PostConstruct;

@Configuration
public class GraphqlScalarConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
         return wiringBuilder -> {
                wiringBuilder.scalar(ExtendedScalars.Json);
                System.out.println(">>> [RuntimeWiringConfigurer] Json scalar registered");
            };
    }

    @PostConstruct
    public void initCheck() {
        System.out.println(">>> GraphqlScalarConfig bean HAS BEEN INITIALIZED.");
    }
    
    @PostConstruct
    public void printSchemas() {
        System.out.println(">>> Loaded GraphQL schemas:");
        try {
            var resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:graphql/*.graphqls");
            for (var resource : resources) {
                System.out.println(" - " + resource.getFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}