package com.orderflow.cozinhaservice.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.orderflow.cozinhaservice.exception.PedidoCozinhaNaoExisteException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@DBUnit(cacheConnection = false)
@DataSet(value = "/datasets/pedido-cozinha.xml", cleanBefore = true, cleanAfter = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoCozinhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void deveFinalizarPreparoPedido() throws Exception {
        mockMvc.perform(put("/api/pedido-cozinha/finalizarPreparo/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void deveRetornarErroSePedidoNaoExistir() throws Exception {
        assertThatThrownBy(() -> mockMvc.perform(put("/api/pedido-cozinha/finalizarPreparo/99")))
                .hasCauseInstanceOf(PedidoCozinhaNaoExisteException.class);
    }

    @Test
    @Order(3)
    void deveRetornarErroSePedidoJaEstiverPronto() throws Exception {
        assertThatThrownBy(() -> mockMvc.perform(put("/api/pedido-cozinha/finalizarPreparo/2")))
                .hasCauseInstanceOf(PedidoCozinhaNaoExisteException.class);
    }
}
