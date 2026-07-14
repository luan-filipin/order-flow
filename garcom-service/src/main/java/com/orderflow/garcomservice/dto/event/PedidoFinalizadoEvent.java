package com.orderflow.garcomservice.dto.event;

import com.orderflow.garcomservice.domain.Status;

import java.time.LocalDateTime;

public record PedidoFinalizadoEvent(
        Long idPedido,
        Integer numeroMesa,
        Status status,
        LocalDateTime dataFimPreparo
) {
}
