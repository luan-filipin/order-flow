package com.orderflow.garcomservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "garcom")
public class Garcom {

    @Column(name = "id_pedido", nullable = false)
    private UUID idPedido;

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @Column(name = "nome_garcom", nullable = false)
    private String nomeGarcom;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private boolean status = true;

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "items_pedido", nullable = false)
    private String itemsPedido;
}
