package com.eventosapi.demo.exceptions;

public class EntidadeNaoEncontradoException extends RuntimeException {
    public EntidadeNaoEncontradoException(String message) {
        super(message);
    }
}
