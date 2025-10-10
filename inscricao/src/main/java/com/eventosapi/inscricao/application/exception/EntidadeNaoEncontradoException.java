package com.eventosapi.inscricao.application.exception;

public class EntidadeNaoEncontradoException extends RuntimeException {
    public EntidadeNaoEncontradoException(String message) { super(message); }
    public EntidadeNaoEncontradoException(String message, Throwable cause) { super(message, cause); }
}
