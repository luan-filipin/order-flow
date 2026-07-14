package com.orderflow.cozinhaservice.dto.response;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoCozinhaResponseDTO(
        UUID id,
        Long idPedido,
        Integer numeroMesa,
        Status status,
        LocalDateTime dataRecebimento,
        LocalDateTime dataFimPreparo
) {
    public static PedidoCozinhaResponseDTO fromDomain(PedidoCozinha pedidoCozinha) {
        return new PedidoCozinhaResponseDTO(
                pedidoCozinha.getId(),
                pedidoCozinha.getIdPedido(),
                pedidoCozinha.getNumeroMesa(),
                pedidoCozinha.getStatus(),
                pedidoCozinha.getDataRecebimento(),
                pedidoCozinha.getDataFimPreparo()
        );
    }
}
