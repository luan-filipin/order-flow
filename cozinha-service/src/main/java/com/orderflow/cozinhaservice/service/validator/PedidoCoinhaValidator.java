package com.orderflow.cozinhaservice.service.validator;

import com.orderflow.cozinhaservice.domain.PedidoCozinha;
import com.orderflow.cozinhaservice.domain.enums.Status;
import com.orderflow.cozinhaservice.exception.PedidoCozinhaNaoExisteException;
import com.orderflow.cozinhaservice.repository.PedidoCozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoCoinhaValidator {

    private final PedidoCozinhaRepository pedidoCozinhaRepository;

    public PedidoCozinha pedidoCozinhaExisteOuLancaException(Long idPedido) {
        return pedidoCozinhaRepository.findByidPedidoAndStatus(idPedido, Status.RECEBIDO)
                .orElseThrow(() -> new PedidoCozinhaNaoExisteException(idPedido));
    }
}
