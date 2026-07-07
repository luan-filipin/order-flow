package com.orderflow.garcomservice.service.impl;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.dto.event.PedidoCriadoEvent;
import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;
import com.orderflow.garcomservice.outbox.domain.OutboxEvent;
import com.orderflow.garcomservice.outbox.domain.enums.StatusEvent;
import com.orderflow.garcomservice.outbox.event.OutboxCreatedEvent;
import com.orderflow.garcomservice.outbox.repository.OutboxEventRepository;
import com.orderflow.garcomservice.repository.GarcomRepository;
import com.orderflow.garcomservice.service.GarcomService;
import com.orderflow.garcomservice.service.validation.GarcomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.JsonUtil;

@RequiredArgsConstructor
@Service
public class GarcomServiceImpl implements GarcomService {

    private final GarcomRepository garcomRepository;
    private final GarcomValidator garcomValidator;
    private final OutboxEventRepository outboxEventRepository;
    private final ApplicationEventPublisher publisher;
    private final JsonUtil jsonUtil;

    @Transactional
    @Override
    public GarcomResponseDTO gerarPedido(GarcomRequestDTO dto) {
        garcomValidator.validaSeMesaJaEstaSendoAtendida(dto.numeroMesa());
        Garcom garcomSalvo = garcomRepository.save(dto.toDomain());

        OutboxEvent outbox = outboxEventRepository.save(
                OutboxEvent.builder()
                        .aggregateId(garcomSalvo.getIdPedido())
                        .eventType("PedidoCriadoEvent")
                        .payload(jsonUtil.toJson(PedidoCriadoEvent.fromDomain(garcomSalvo)))
                        .status(StatusEvent.PENDENTE)
                        .build()
        );

        publisher.publishEvent(new OutboxCreatedEvent(outbox.getId()));
        return GarcomResponseDTO.fromDomain(garcomSalvo);
    }

    @Override
    public GarcomResponseDTO buscarPedidoPeloNumeroMesa(Integer numeroMesa) {
        Garcom garcom = garcomValidator.buscaPedidoPeloNumeroMesaOuLancaExcecao(numeroMesa);
        return GarcomResponseDTO.fromDomain(garcom);
    }

    @Override
    public GarcomResponseDTO encerraPedido(Integer numeroMesa) {
        Garcom garcom = garcomValidator.buscaPedidoPeloNumeroMesaOuLancaExcecao(numeroMesa);
        garcomValidator.validaSePedidoJaNaoEstaEncerrado(garcom.isStatus());
        garcom.setStatus(false);
        return GarcomResponseDTO.fromDomain(garcomRepository.save(garcom));
    }
}
