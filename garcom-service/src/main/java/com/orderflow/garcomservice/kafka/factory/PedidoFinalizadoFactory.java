package com.orderflow.garcomservice.kafka.factory;

import com.orderflow.garcomservice.dto.event.PedidoFinalizadoEvent;
import com.orderflow.garcomservice.kafka.config.KafkaConsumerInfrastructure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class PedidoFinalizadoFactory {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PedidoFinalizadoEvent> pedidoFinalizadoListenerFactory(
            KafkaConsumerInfrastructure config) {
        return config.listenerFactory(PedidoFinalizadoEvent.class);
    }
}
