package com.orderflow.garcomservice.dto.response;

public record ErroCampoDTO(
        String campo,
        String mensagem
) {
}
