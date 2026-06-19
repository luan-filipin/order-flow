package com.orderflow.garcomservice.exception;

import java.util.UUID;

public class OPedidoNaoExistePeloIdException extends RuntimeException {
    public OPedidoNaoExistePeloIdException(UUID idPedido) {
        super("O pedido com o id: "+ idPedido + " nao existe.");
    }
}
