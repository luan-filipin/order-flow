package com.orderflow.cozinhaservice.kafka.consumer;

import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;
import com.orderflow.cozinhaservice.service.PedidoCozinhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PedidoRecebidoConsumer {

    private final PedidoCozinhaService pedidoCozinhaService;

    @KafkaListener(topics = "${kafka.topic.pedido-criado}", containerFactory = "pedidoRecebidoListenerFactory")
    public void consumirPedidoCriado(PedidoRecebidoEvent event) {
        try {
            pedidoCozinhaService.gerarPedido(event);
            log.info("Pedido criado com sucesso: {}", event);
        } catch (Exception ex) {
            log.error("Falha ao criar o pedido: {}", event.idPedido(), ex);
            throw ex;
        }
    }
}