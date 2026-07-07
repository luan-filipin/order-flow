package com.orderflow.cozinhaservice.repository;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoCozinhaRepository extends JpaRepository<PedidoCozinha, UUID> {

    Optional<PedidoCozinha> findByidPedidoAndStatus(Long idPedido, Status status);
}
