package com.orderflow.garcomservice.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.orderflow.garcomservice.fixture.GarcomFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@DataSet("/datasets/garcom.xml")
class GarcomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveGerarPedido() throws Exception {
        mockMvc.perform(post("/api/garcom/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GarcomFixture.gerarPedidoDto(3, "Garcom 3"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroMesa").value(3))
                .andExpect(jsonPath("$.nomeGarcom").value("Garcom 3"));
    }

    @Test
    void deveLancarErroSePedidoJaExistir() throws Exception {
        mockMvc.perform(post("/api/garcom/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GarcomFixture.gerarPedidoDto(1, "Garcom 1"))))
                .andExpect(status().isConflict());
    }

    @Test
    void deveBuscarPedidoMesa() throws Exception {
        mockMvc.perform(get("/api/garcom/buscarPedidoMesa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroMesa").value(1))
                .andExpect(jsonPath("$.nomeGarcom").value("Garcom 1"));

    }

    @Test
    void deveLancarErroSeMesaNaoExistir() throws Exception {
        mockMvc.perform(get("/api/garcom/buscarPedidoMesa/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveEncerrarPedido() throws Exception {

        mockMvc.perform(put("/api/garcom/encerrarPedido/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("false"));
    }

    @Test
    void deveLancarErroSePedidoNaoExistir() throws Exception {
        mockMvc.perform(put("/api/garcom/encerrarPedido/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveLancarErroSePedidoJaEstaEncerrado() throws Exception {
        mockMvc.perform(put("/api/garcom/encerrarPedido/2"))
                .andExpect(status().isConflict());
    }
}
