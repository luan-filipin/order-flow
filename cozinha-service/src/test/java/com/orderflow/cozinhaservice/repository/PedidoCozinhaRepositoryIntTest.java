package com.orderflow.cozinhaservice.repository;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DBRider
@DBUnit(cacheConnection = false)
@DataSet(value = "/datasets/pedido-cozinha.xml", cleanBefore = true, cleanAfter = true)
class PedidoCozinhaRepositoryIntTest {

    @Autowired
    private PedidoCozinhaRepository pedidoCozinhaRepository;

    @Test
    void deveRetornarPedidoRecebidoPeloIdPedido() {
        Optional<PedidoCozinha> pedido = pedidoCozinhaRepository.findByidPedidoAndStatus(1L, Status.RECEBIDO);

        assertThat(pedido).isPresent();
        assertThat(pedido.get().getIdPedido()).isEqualTo(1L);
        assertThat(pedido.get().getStatus()).isEqualTo(Status.RECEBIDO);
    }

    @Test
    void deveRetornarVazioQuandoPedidoNaoExistir() {
        Optional<PedidoCozinha> pedido = pedidoCozinhaRepository.findByidPedidoAndStatus(99L, Status.RECEBIDO);

        assertThat(pedido).isEmpty();
    }

    @Test
    void deveRetornarVazioQuandoPedidoNaoEstiverRecebido() {
        Optional<PedidoCozinha> pedido = pedidoCozinhaRepository.findByidPedidoAndStatus(2L, Status.RECEBIDO);

        assertThat(pedido).isEmpty();
    }
}
