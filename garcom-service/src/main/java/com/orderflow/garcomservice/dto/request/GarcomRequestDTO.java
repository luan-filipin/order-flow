package com.orderflow.garcomservice.dto.request;

import com.orderflow.garcomservice.domain.Garcom;

public record GarcomRequestDTO(
        Integer numeroMesa,
        String nomeGarcom,
        String itemsPedido
) {
    public Garcom toDomain(){
        return Garcom.builder()
                .numeroMesa(numeroMesa)
                .nomeGarcom(nomeGarcom)
                .itemsPedido(itemsPedido)
                .build();
    }
}
