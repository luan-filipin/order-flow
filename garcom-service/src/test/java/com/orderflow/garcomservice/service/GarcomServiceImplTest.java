package com.orderflow.garcomservice.service;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;
import com.orderflow.garcomservice.exception.MesaJaEstaSendoAtendidaException;
import com.orderflow.garcomservice.exception.OPedidoJaEstaEncerrado;
import com.orderflow.garcomservice.exception.OPedidoNaoExistePeloNumeroMesaException;
import com.orderflow.garcomservice.fixture.GarcomFixture;
import com.orderflow.garcomservice.repository.GarcomRepository;
import com.orderflow.garcomservice.service.impl.GarcomServiceImpl;
import com.orderflow.garcomservice.service.validation.GarcomValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GarcomServiceImplTest {

    @Mock
    private GarcomRepository garcomRepository;

    private GarcomService garcomService;


    @BeforeEach
    void setUp() {
        GarcomValidator garcomValidator = new GarcomValidator(garcomRepository);
        garcomService = new GarcomServiceImpl(garcomRepository, garcomValidator);
    }

    @Test
    void deveGerarPedido() {
        GarcomRequestDTO garcomRequestDTO = GarcomFixture.gerarPedidoDto(1, "Garcom 1");
        Garcom garcom = GarcomFixture.gerarPedidoDomain(1L, 1, "Garcom 1", true, "item 1");

        when(garcomRepository.save(any(Garcom.class))).thenReturn(garcom);

        GarcomResponseDTO resultado = garcomService.gerarPedido(garcomRequestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.idPedido()).isEqualTo(1L);
        assertThat(resultado.numeroMesa()).isEqualTo(1);

        verify(garcomRepository).save(any(Garcom.class));
        verify(garcomRepository).existsByNumeroMesaAndStatusTrue(1);
    }

    @Test
    void deveLancarErroSeMesaJaEstiverSendoAtendida() {
        GarcomRequestDTO garcomRequestDTO = GarcomFixture.gerarPedidoDto(1, "Garcom 1");

        when(garcomRepository.existsByNumeroMesaAndStatusTrue(1)).thenReturn(true);

        assertThatThrownBy(() -> garcomService.gerarPedido(garcomRequestDTO))
                .isInstanceOf(MesaJaEstaSendoAtendidaException.class);
    }

    @Test
    void deveBuscarPedidoPeloNumeroMesa() {
        Garcom garcom = GarcomFixture.gerarPedidoDomain(1L, 1, "Garcom 1", true, "item 1");

        when(garcomRepository.findByNumeroMesa(1)).thenReturn(Optional.of(garcom));

        GarcomResponseDTO resultado = garcomService.buscarPedidoPeloNumeroMesa(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.idPedido()).isEqualTo(1L);
        assertThat(resultado.numeroMesa()).isEqualTo(1);

        verify(garcomRepository).findByNumeroMesa(1);
    }

    @Test
    void deveLancarErroSePedidoPeloNumeroMesaNaoExistir() {
        when(garcomRepository.findByNumeroMesa(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> garcomService.buscarPedidoPeloNumeroMesa(1))
                .isInstanceOf(OPedidoNaoExistePeloNumeroMesaException.class)
                .hasMessage("O pedido para a mesa: 1 nao existe.");
    }

    @Test
    void deveEncerrarPedido() {
        Garcom garcom = GarcomFixture.gerarPedidoDomain(1L, 1, "Garcom 1", true, "item 1");

        when(garcomRepository.findByNumeroMesa(1)).thenReturn(Optional.of(garcom));
        when(garcomRepository.save(any(Garcom.class))).thenReturn(garcom);

        GarcomResponseDTO resultado = garcomService.encerraPedido(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.status()).isFalse();

        verify(garcomRepository).findByNumeroMesa(1);
        verify(garcomRepository).save(any(Garcom.class));
    }

    @Test
    void deveLancarErroSePedidoNaoExistirParaEncerrar() {
        when(garcomRepository.findByNumeroMesa(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> garcomService.encerraPedido(1))
                .isInstanceOf(OPedidoNaoExistePeloNumeroMesaException.class)
                .hasMessage("O pedido para a mesa: 1 nao existe.");
    }

    @Test
    void deveLancarErroSePedidoJaEstaEncerrado() {
        Garcom garcom = GarcomFixture.gerarPedidoDomain(1L, 1, "Garcom 1", false, "item 1");

        when(garcomRepository.findByNumeroMesa(1)).thenReturn(Optional.of(garcom));

        assertThatThrownBy(() -> garcomService.encerraPedido(1))
                .isInstanceOf(OPedidoJaEstaEncerrado.class)
                .hasMessage("O pedido ja esta encerrado.");
    }
}
