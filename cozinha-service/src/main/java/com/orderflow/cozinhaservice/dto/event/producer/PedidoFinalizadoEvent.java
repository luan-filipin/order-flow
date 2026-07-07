package com.orderflow.cozinhaservice.dto.event.producer;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;

import java.time.LocalDateTime;

public record PedidoFinalizadoEvent(
        Long idPedido,
        Status status,
        LocalDateTime dataFimPreparo
) {
    public static PedidoFinalizadoEvent fromDomain(PedidoCozinha pedidoCozinha) {
        return new PedidoFinalizadoEvent(
                pedidoCozinha.getIdPedido(),
                pedidoCozinha.getStatus(),
                pedidoCozinha.getDataFimPreparo()
        );
    }
}
