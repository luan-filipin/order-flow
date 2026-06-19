package com.orderflow.garcomservice.service;

import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;

import java.util.UUID;

public interface GarcomService {

    GarcomResponseDTO gerarPedido(GarcomRequestDTO dto);
    GarcomResponseDTO buscarPedidoPeloNumeroMesa(Integer numeroMesa);
    GarcomResponseDTO buscarPedidoPeloId(UUID idPedido);
    GarcomResponseDTO encerraPedido(Integer numeroMesa);
}
