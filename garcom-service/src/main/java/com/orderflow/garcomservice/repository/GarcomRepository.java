package com.orderflow.garcomservice.repository;

import com.orderflow.garcomservice.domain.Garcom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GarcomRepository extends JpaRepository<Garcom, UUID> {

    boolean existsByNumeroMesaAndStatusTrue(Integer numeroMesa);
    Optional<Garcom> findByNumeroMesa(Integer numeroMesa);
    Optional<Garcom> findByIdPedido(UUID idPedido);
    boolean existsByNumeroMesaAndStatusFalse(Integer numeroMesa);
}
