package com.orderflow.garcomservice.fixture;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class GarcomFixture {

    public static GarcomRequestDTO gerarPedidoDto(Integer numeroMesa, String nomeGarcom){
        return new GarcomRequestDTO(
                numeroMesa,
                nomeGarcom,
                "item 1 teste");
    }

    public static Garcom gerarPedidoDomain(
            String idPedido,
            Integer numeroMesa,
            String nomeGarcom,
            boolean status,
            String itemsPedido){
        return new Garcom(
                UUID.fromString(idPedido),
                numeroMesa,
                nomeGarcom,
                status,
                LocalDateTime.of(2026, 5, 15, 1, 1, 1),
                itemsPedido
        );
    }
}
