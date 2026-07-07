package com.orderflow.cozinhaservice.service;

import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;
import com.orderflow.cozinhaservice.dto.response.PedidoCozinhaResponseDTO;

public interface PedidoCozinhaService {

    void gerarPedido(PedidoRecebidoEvent event);

    PedidoCozinhaResponseDTO finalizarPreparoPedido(Long idPedido);
}
