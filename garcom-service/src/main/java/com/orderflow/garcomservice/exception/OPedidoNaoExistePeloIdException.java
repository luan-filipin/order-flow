package com.orderflow.garcomservice.exception;

public class OPedidoNaoExistePeloIdException extends RuntimeException {
    public OPedidoNaoExistePeloIdException(Long idPedido) {
        super("O pedido com o id: "+ idPedido + " nao existe.");
    }
}
