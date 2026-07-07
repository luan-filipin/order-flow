package com.orderflow.garcomservice.kafka.producer;

import com.orderflow.garcomservice.dto.event.PedidoCriadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PedidoCriadoProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.pedido-criado}")
    private String topic;

    public void enviarPedidoCriado(PedidoCriadoEvent event) {
        kafkaTemplate.send(topic, event).whenComplete((result, ex) -> {
            if (ex == null) {
                logSucesso(event, result.getRecordMetadata().partition());
            } else {
                logFalha(event, ex);
            }
        });
    }

    private void logSucesso(PedidoCriadoEvent event, int particao) {
        log.info("Pedido {} enviado com sucesso para o tópico {}, partição {}",
                event.idPedido(), topic, particao);
    }

    private void logFalha(PedidoCriadoEvent event, Throwable ex) {
        log.error("Falha ao enviar pedido {} para o Kafka", event.idPedido(), ex);
    }

}

