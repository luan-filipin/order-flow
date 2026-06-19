package com.orderflow.garcomservice.exception;

public class OPedidoNaoExistePeloNumeroMesaException extends RuntimeException {
    public OPedidoNaoExistePeloNumeroMesaException(Integer numeroMesa) {
        super("O pedido para a mesa: "+ numeroMesa + " nao existe.");
    }
}
