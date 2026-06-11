package com.orderflow.restauranteservice;

import org.springframework.boot.SpringApplication;

public class TestRestauranteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(RestauranteServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
