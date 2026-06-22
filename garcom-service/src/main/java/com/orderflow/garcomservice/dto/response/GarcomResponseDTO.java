package com.orderflow.garcomservice.dto.response;

import com.orderflow.garcomservice.domain.Garcom;

import java.time.LocalDateTime;
import java.util.UUID;

public record GarcomResponseDTO(
        Long idPedido,
        Integer numeroMesa,
        String nomeGarcom,
        boolean status,
        LocalDateTime dataPedido,
        String itemsPedido
) {
    public static GarcomResponseDTO fromDomain(Garcom garcom){
        return new GarcomResponseDTO(
                garcom.getIdPedido(),
                garcom.getNumeroMesa(),
                garcom.getNomeGarcom(),
                garcom.isStatus(),
                garcom.getDataPedido(),
                garcom.getItemsPedido());
    }
}
