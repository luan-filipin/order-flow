package com.orderflow.cozinhaservice.outbox.listener;

import com.orderflow.cozinhaservice.outbox.domain.OutboxEvent;
import com.orderflow.cozinhaservice.outbox.domain.enums.StatusEvent;
import com.orderflow.cozinhaservice.outbox.event.OutboxCreatedEvent;
import com.orderflow.cozinhaservice.outbox.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class OutboxCreatedEventListener {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    @EventListener
    public void ouveOutboxCreatedEvent(OutboxCreatedEvent event) {
        OutboxEvent outbox = outboxEventRepository.findById(event.getOutboxId())
                .orElseThrow(() -> new RuntimeException("Outbox not found"));

        kafkaTemplate.send("pedido-finalizado", outbox.getPayload());
        outbox.setStatus(StatusEvent.ENVIADO);
        outbox.setSendAt(LocalDateTime.now());

        outboxEventRepository.save(outbox);
    }
}
