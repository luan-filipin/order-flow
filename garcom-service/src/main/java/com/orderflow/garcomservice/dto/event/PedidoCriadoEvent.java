package com.orderflow.garcomservice.dto.event;

import com.orderflow.garcomservice.domain.Garcom;

public record PedidoCriadoEvent(
        Long idPedido,
        Integer numeroMesa,
        String itemsPedido
) {
    public static PedidoCriadoEvent fromDomain(Garcom garcom) {
        return new PedidoCriadoEvent(
                garcom.getIdPedido(),
                garcom.getNumeroMesa(),
                garcom.getItemsPedido()
        );
    }
}
