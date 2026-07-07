package com.orderflow.garcomservice.outbox.event;

public class OutboxCreatedEvent {

    private final Long outboxId;

    public OutboxCreatedEvent(Long outboxId) {
        this.outboxId = outboxId;
    }

    public Long getOutboxId() {
        return outboxId;
    }
}
