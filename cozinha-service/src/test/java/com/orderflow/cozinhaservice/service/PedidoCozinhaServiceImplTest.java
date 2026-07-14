package com.orderflow.cozinhaservice.service;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;
import com.orderflow.cozinhaservice.dto.response.PedidoCozinhaResponseDTO;
import com.orderflow.cozinhaservice.exception.PedidoCozinhaNaoExisteException;
import com.orderflow.cozinhaservice.fixture.PedidoCozinhaFixture;
import com.orderflow.cozinhaservice.outbox.domain.OutboxEvent;
import com.orderflow.cozinhaservice.outbox.event.OutboxCreatedEvent;
import com.orderflow.cozinhaservice.outbox.repository.OutboxEventRepository;
import com.orderflow.cozinhaservice.repository.PedidoCozinhaRepository;
import com.orderflow.cozinhaservice.service.impl.PedidoCozinhaServiceImpl;
import com.orderflow.cozinhaservice.service.validator.PedidoCoinhaValidator;
import com.orderflow.cozinhaservice.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoCozinhaServiceImplTest {

    @Mock
    private PedidoCozinhaRepository pedidoCozinhaRepository;

    @Mock
    private OutboxEventRepository outboxEventRepository;

    private CapturingApplicationEventPublisher publisher;
    private PedidoCozinhaService pedidoCozinhaService;

    @BeforeEach
    void setUp() {
        PedidoCoinhaValidator pedidoCoinhaValidator = new PedidoCoinhaValidator(pedidoCozinhaRepository);
        publisher = new CapturingApplicationEventPublisher();
        JsonUtil jsonUtil = new JsonUtil(new ObjectMapper());
        pedidoCozinhaService = new PedidoCozinhaServiceImpl(
                pedidoCozinhaRepository,
                pedidoCoinhaValidator,
                outboxEventRepository,
                publisher,
                jsonUtil
        );
    }

    @Test
    void deveGerarPedido() {
        PedidoRecebidoEvent event = PedidoCozinhaFixture.pedidoRecebidoEvent(1L, 1);
        PedidoCozinha pedido = PedidoCozinhaFixture.pedidoCozinhaDomain(
                UUID.randomUUID(),
                1L,
                1,
                "item 1 teste",
                Status.RECEBIDO
        );

        when(pedidoCozinhaRepository.save(any(PedidoCozinha.class))).thenReturn(pedido);

        pedidoCozinhaService.gerarPedido(event);

        verify(pedidoCozinhaRepository).save(any(PedidoCozinha.class));
    }

    @Test
    void deveFinalizarPreparoPedido() {
        PedidoCozinha pedido = PedidoCozinhaFixture.pedidoCozinhaDomain(
                UUID.randomUUID(),
                1L,
                1,
                "item 1 teste",
                Status.RECEBIDO
        );

        when(pedidoCozinhaRepository.findByidPedidoAndStatus(1L, Status.RECEBIDO)).thenReturn(Optional.of(pedido));
        when(outboxEventRepository.save(any(OutboxEvent.class))).thenAnswer(invocation -> {
            OutboxEvent outbox = invocation.getArgument(0);
            outbox.setId(10L);
            return outbox;
        });

        PedidoCozinhaResponseDTO resultado = pedidoCozinhaService.finalizarPreparoPedido(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.idPedido()).isEqualTo(1L);
        assertThat(resultado.status()).isEqualTo(Status.PRONTO);
        assertThat(resultado.dataFimPreparo()).isNotNull();

        verify(pedidoCozinhaRepository).findByidPedidoAndStatus(1L, Status.RECEBIDO);
        verify(outboxEventRepository).save(any(OutboxEvent.class));
        assertThat(publisher.event()).isInstanceOf(OutboxCreatedEvent.class);
    }

    @Test
    void deveLancarErroSePedidoNaoExistirParaFinalizarPreparo() {
        when(pedidoCozinhaRepository.findByidPedidoAndStatus(99L, Status.RECEBIDO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoCozinhaService.finalizarPreparoPedido(99L))
                .isInstanceOf(PedidoCozinhaNaoExisteException.class)
                .hasMessageContaining("99");
    }

    private static class CapturingApplicationEventPublisher implements ApplicationEventPublisher {

        private Object event;

        @Override
        public void publishEvent(Object event) {
            this.event = event;
        }

        Object event() {
            return event;
        }
    }
}
