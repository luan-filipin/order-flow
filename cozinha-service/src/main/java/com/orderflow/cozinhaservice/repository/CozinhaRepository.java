package com.orderflow.cozinhaservice.repository;

import com.orderflow.cozinhaservice.domain.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CozinhaRepository extends JpaRepository <Cozinha, UUID>{
}
