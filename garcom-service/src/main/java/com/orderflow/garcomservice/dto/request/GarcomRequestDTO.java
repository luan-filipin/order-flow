package com.orderflow.garcomservice.dto.request;

import com.orderflow.garcomservice.domain.Garcom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GarcomRequestDTO(
        @NotNull(message = "O numero da mesa é obrigatorio")
        Integer numeroMesa,
        @NotBlank(message = "O nome do garcom é obrigatorio")
        String nomeGarcom,
        @NotBlank(message = "O item do pedido é obrigatorio")
        String itemsPedido
) {
    public Garcom toDomain() {
        return Garcom.builder()
                .numeroMesa(numeroMesa)
                .nomeGarcom(nomeGarcom)
                .itemsPedido(itemsPedido)
                .build();
    }
}
