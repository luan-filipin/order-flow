package com.orderflow.garcomservice.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.orderflow.garcomservice.domain.Garcom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DBRider
@DataSet("/datasets/garcom.xml")
class GarcomRepositoryIntTest {

    @Autowired
    private GarcomRepository garcomRepository;

    @Test
    void verificaSeMesaJaEstaSendoAtendida() {
        boolean existe = garcomRepository.existsByNumeroMesaAndStatusTrue(1);
        assertThat(existe).isTrue();
    }

    @Test
    void deveRetornarFalseSeMesaEstiverLivre() {
        boolean existe = garcomRepository.existsByNumeroMesaAndStatusTrue(2);
        assertThat(existe).isFalse();
    }

    @Test
    void deveRetornarGarcomPeloNumeroMesa() {
        Optional<Garcom> garcom = garcomRepository.findByNumeroMesa(1);
        assertThat(garcom).isPresent();
        assertThat(garcom.get().getNumeroMesa()).isEqualTo(1);
    }

    @Test
    void deveRetorarVazioQuandoMesaNaoExistir() {
        Optional<Garcom> garcom = garcomRepository.findByNumeroMesa(99);
        assertThat(garcom).isEmpty();
    }
}
