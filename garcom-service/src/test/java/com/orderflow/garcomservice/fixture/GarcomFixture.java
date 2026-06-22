package com.orderflow.garcomservice.fixture;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;

import java.time.LocalDateTime;

public class GarcomFixture {

    public static GarcomRequestDTO gerarPedidoDto(Integer numeroMesa, String nomeGarcom) {
        return new GarcomRequestDTO(
                numeroMesa,
                nomeGarcom,
                "item 1 teste");
    }

    public static Garcom gerarPedidoDomain(
            Long idPedido,
            Integer numeroMesa,
            String nomeGarcom,
            boolean status,
            String itemsPedido) {
        return new Garcom(
                idPedido,
                numeroMesa,
                nomeGarcom,
                status,
                LocalDateTime.of(2026, 5, 15, 1, 1, 1),
                itemsPedido
        );
    }
}
