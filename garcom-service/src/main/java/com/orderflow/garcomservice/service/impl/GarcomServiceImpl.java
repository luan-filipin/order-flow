package com.orderflow.garcomservice.service.impl;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;
import com.orderflow.garcomservice.repository.GarcomRepository;
import com.orderflow.garcomservice.service.GarcomService;
import com.orderflow.garcomservice.service.validation.GarcomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GarcomServiceImpl implements GarcomService {

    private final GarcomRepository garcomRepository;
    private final GarcomValidator garcomValidator;

    @Override
    public GarcomResponseDTO gerarPedido(GarcomRequestDTO dto) {
        garcomValidator.validaSeMesaJaEstaSendoAtendida(dto.numeroMesa());
        Garcom garcomSalvo = garcomRepository.save(dto.toDomain());
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
