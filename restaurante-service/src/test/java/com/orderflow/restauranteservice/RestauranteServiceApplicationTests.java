package com.orderflow.restauranteservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class RestauranteServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
