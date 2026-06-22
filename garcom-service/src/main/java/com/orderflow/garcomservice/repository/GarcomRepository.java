package com.orderflow.garcomservice.repository;

import com.orderflow.garcomservice.domain.Garcom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarcomRepository extends JpaRepository<Garcom, Long> {

    boolean existsByNumeroMesaAndStatusTrue(Integer numeroMesa);

    Optional<Garcom> findByNumeroMesa(Integer numeroMesa);

    Optional<Garcom> findByIdPedido(Long idPedido);
}
