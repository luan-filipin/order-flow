package com.orderflow.garcomservice.outbox.repository;

import com.orderflow.garcomservice.outbox.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
}
