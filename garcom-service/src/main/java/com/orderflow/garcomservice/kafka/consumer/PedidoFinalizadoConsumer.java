package com.orderflow.garcomservice.kafka.consumer;

import com.orderflow.garcomservice.dto.event.PedidoFinalizadoEvent;
import com.orderflow.garcomservice.service.GarcomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PedidoFinalizadoConsumer {

    private final GarcomService garcomService;

    @KafkaListener(topics = "${kafka.topic.pedido-finalizado}", containerFactory = "pedidoFinalizadoListenerFactory")
    public void consumirPedidoFinalizado(PedidoFinalizadoEvent event) {
        try {
            garcomService.encerraPedido(event.numeroMesa());
            log.info("Pedido finalizado com sucesso: {}", event);
        } catch (Exception ex) {
            log.error("Falha ao finalizar o pedido: {}", event.numeroMesa(), ex);
            throw ex;
        }

    }
}
