package com.example.graphql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlDemoApplication.class, args);
    }

//    @Bean
//    CommandLineRunner initData(BookRepository repo) {
//        return args -> {
//            repo.save(new Book(1L, "Clean Code", "Robert C. Martin", 464));
//            repo.save(new Book(2L, "Effective Java", "Joshua Bloch", 416));
//            repo.save(new Book(3L, "GraphQL in Action", "Samer Buna", 360));
//        };
//    }
}
