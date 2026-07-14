package com.orderflow.cozinhaservice.fixture;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoCozinhaFixture {

    public static PedidoRecebidoEvent pedidoRecebidoEvent(Long idPedido, Integer numeroMesa) {
        return new PedidoRecebidoEvent(
                idPedido,
                numeroMesa,
                "item 1 teste"
        );
    }

    public static PedidoCozinha pedidoCozinhaDomain(
            UUID id,
            Long idPedido,
            Integer numeroMesa,
            String itemsPedido,
            Status status) {
        return new PedidoCozinha(
                id,
                idPedido,
                numeroMesa,
                itemsPedido,
                status,
                LocalDateTime.of(2026, 5, 15, 1, 1, 1),
                null
        );
    }
}
