package com.ctbe.productservice;

import com.ctbe.productservice.model.product;
import com.ctbe.productservice.repository.ProductRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
    title = "Product Service API",
    version = "1.0.0",
    description = "RESTful Product Catalogue - Lab 2"
))
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(ProductRepository repo) {
        return args -> {
            repo.save(new product("Laptop", 1200.00, 10, "Electronics"));
            repo.save(new product("Monitor", 350.00, 5, "Electronics"));
            repo.save(new product("Keyboard", 85.00, 20, "Peripherals"));
            repo.save(new product("Mouse", 25.99, 50, "Peripherals"));
        };
    }
}