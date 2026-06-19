package com.orderflow.cozinhaservice.domain;

import com.orderflow.cozinhaservice.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cozinha")
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_pedido", nullable = false)
    private UUID idPedido;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @Column(name = "items_pedido", nullable = false)
    private String itemsPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(name = "data_recebimento", nullable = false)
    private LocalDateTime dataRecebimento;

    @Column(name = "data_fim_preparo")
    private LocalDateTime dataFimPreparo;
}
