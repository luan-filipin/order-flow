package com.orderflow.garcomservice.service.validation;

import com.orderflow.garcomservice.domain.Garcom;
import com.orderflow.garcomservice.exception.MesaJaEstaSendoAtendidaException;
import com.orderflow.garcomservice.exception.OPedidoJaEstaEncerrado;
import com.orderflow.garcomservice.exception.OPedidoNaoExistePeloIdException;
import com.orderflow.garcomservice.exception.OPedidoNaoExistePeloNumeroMesaException;
import com.orderflow.garcomservice.repository.GarcomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GarcomValidator {

    private final GarcomRepository garcomRepository;

    public void validaSeMesaJaEstaSendoAtendida(Integer numeroMesa) {
        if (garcomRepository.existsByNumeroMesaAndStatusTrue(numeroMesa)){
            throw new MesaJaEstaSendoAtendidaException(numeroMesa);
        }
    }

    public Garcom buscaPedidoPeloNumeroMesaOuLancaExcecao(Integer numeroMesa){
        return garcomRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() -> new OPedidoNaoExistePeloNumeroMesaException(numeroMesa));
    }

    public Garcom buscaPedidoPeloIdOuLancaException(UUID idPedido){
        return garcomRepository.findByIdPedido(idPedido)
                .orElseThrow(() -> new OPedidoNaoExistePeloIdException(idPedido));
    }

    public void validaSePedidoJaNaoEstaEncerrado(boolean statusPedido){
        if (!statusPedido){
            throw new OPedidoJaEstaEncerrado();
        }
    }
}
