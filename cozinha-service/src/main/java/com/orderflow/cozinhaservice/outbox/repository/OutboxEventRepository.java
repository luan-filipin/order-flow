package com.orderflow.cozinhaservice.outbox.repository;

import com.orderflow.cozinhaservice.outbox.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
}
