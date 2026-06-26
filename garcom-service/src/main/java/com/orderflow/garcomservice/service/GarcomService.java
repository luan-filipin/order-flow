package com.orderflow.garcomservice.service;

import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;

public interface GarcomService {

    GarcomResponseDTO gerarPedido(GarcomRequestDTO dto);

    GarcomResponseDTO buscarPedidoPeloNumeroMesa(Integer numeroMesa);

    GarcomResponseDTO encerraPedido(Integer numeroMesa);
}
