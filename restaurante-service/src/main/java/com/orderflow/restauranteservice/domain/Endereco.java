package com.orderflow.restauranteservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Embeddable
public class Endereco {

    @Column(nullable = false, length = 100)
    private String rua;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(nullable = false, length = 50)
    private String bairro;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(length = 100)
    private String complemento;

    @Column(name = "ponto_referencia", length = 100)
    private String pontoReferencia;
}
