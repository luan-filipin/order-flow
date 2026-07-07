package com.orderflow.cozinhaservice.kafka.factory;

import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;
import com.orderflow.cozinhaservice.kafka.config.KafkaConsumerInfrastructure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class PedidoRecebidoFactory {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PedidoRecebidoEvent> pedidoRecebidoListenerFactory(KafkaConsumerInfrastructure config) {
        return config.listenerFactory(PedidoRecebidoEvent.class);
    }
}
