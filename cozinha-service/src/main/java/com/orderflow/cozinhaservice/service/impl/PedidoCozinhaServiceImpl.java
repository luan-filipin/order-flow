package com.orderflow.cozinhaservice.service.impl;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import com.orderflow.cozinhaservice.dto.event.consumer.PedidoRecebidoEvent;
import com.orderflow.cozinhaservice.dto.event.producer.PedidoFinalizadoEvent;
import com.orderflow.cozinhaservice.dto.response.PedidoCozinhaResponseDTO;
import com.orderflow.cozinhaservice.outbox.domain.OutboxEvent;
import com.orderflow.cozinhaservice.outbox.domain.enums.StatusEvent;
import com.orderflow.cozinhaservice.outbox.event.OutboxCreatedEvent;
import com.orderflow.cozinhaservice.outbox.repository.OutboxEventRepository;
import com.orderflow.cozinhaservice.repository.PedidoCozinhaRepository;
import com.orderflow.cozinhaservice.service.PedidoCozinhaService;
import com.orderflow.cozinhaservice.service.validator.PedidoCoinhaValidator;
import com.orderflow.cozinhaservice.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PedidoCozinhaServiceImpl implements PedidoCozinhaService {

    private final PedidoCozinhaRepository pedidoCozinhaRepository;
    private final PedidoCoinhaValidator pedidoCoinhaValidator;
    private final OutboxEventRepository outboxEventRepository;
    private final ApplicationEventPublisher publisher;
    private final JsonUtil jsonUtil;

    @Transactional
    @Override
    public void gerarPedido(PedidoRecebidoEvent event) {
        PedidoCozinha pedido = PedidoCozinha.builder()
                .idPedido(event.idPedido())
                .numeroMesa(event.numeroMesa())
                .itemsPedido(event.itemsPedido())
                .status(Status.RECEBIDO)
                .build();
        pedidoCozinhaRepository.save(pedido);
    }

    @Transactional
    @Override
    public PedidoCozinhaResponseDTO finalizarPreparoPedido(Long idPedido) {
        PedidoCozinha pedido = pedidoCoinhaValidator.pedidoCozinhaExisteOuLancaException(idPedido);
        pedido.setStatus(Status.PRONTO);
        pedido.setDataFimPreparo(LocalDateTime.now());

        OutboxEvent outbox = outboxEventRepository.save(
                OutboxEvent.builder()
                        .aggregateId(pedido.getIdPedido())
                        .eventType("PedidoFinalizadoEvent")
                        .payload(jsonUtil.toJson(PedidoFinalizadoEvent.fromDomain(pedido)))
                        .status(StatusEvent.PENDENTE)
                        .build()
        );

        publisher.publishEvent(new OutboxCreatedEvent(outbox.getId()));
        return PedidoCozinhaResponseDTO.fromDomain(pedido);
    }
}
