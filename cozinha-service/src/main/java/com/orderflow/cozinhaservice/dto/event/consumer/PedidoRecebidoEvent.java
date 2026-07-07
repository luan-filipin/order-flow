package com.orderflow.cozinhaservice.dto.event.consumer;

public record PedidoRecebidoEvent(
        Long idPedido,
        Integer numeroMesa,
        String itemsPedido
) {
}
