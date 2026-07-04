package com.techlab.spring.exception;

public class PedidoEstadoInvalidoException extends RuntimeException{
    public PedidoEstadoInvalidoException(String message) {
        super(message);
    }
}
