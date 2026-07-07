package com.orderflow.cozinhaservice.exception;

public class PedidoCozinhaNaoExisteException extends RuntimeException {
    public PedidoCozinhaNaoExisteException(Long idPedido) {
        super("O Pedido: " + idPedido + " não foi encontrado.");
    }
}
