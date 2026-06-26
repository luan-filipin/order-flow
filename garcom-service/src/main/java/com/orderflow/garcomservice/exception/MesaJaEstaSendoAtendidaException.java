package com.orderflow.garcomservice.exception;

public class MesaJaEstaSendoAtendidaException extends RuntimeException {
    public MesaJaEstaSendoAtendidaException(Integer numeroMesa) {
        super("A mesa: "+ numeroMesa + " ja esta sendo atendida.");
    }
}
