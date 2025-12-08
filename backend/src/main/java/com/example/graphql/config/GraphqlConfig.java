package com.example.graphql.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphqlConfig {

    @Bean
    public GraphQLScalarType jsonObjectScalar() {
        // Align the scalar name with the schema's `JSONObject` declaration so Spring registers it automatically.
        return ExtendedScalars.Json.withName("JSONObject");
    }
}
