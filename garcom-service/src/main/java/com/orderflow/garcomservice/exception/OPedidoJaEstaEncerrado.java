package com.orderflow.garcomservice.exception;

public class OPedidoJaEstaEncerrado extends RuntimeException {
    public OPedidoJaEstaEncerrado() {
        super("O pedido ja esta encerrado.");
    }
}
