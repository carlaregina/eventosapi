package com.eventosapi.inscricao.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class EntidadeNaoEncontradoException extends RuntimeException {
    public EntidadeNaoEncontradoException(String message) { super(message); }
    public EntidadeNaoEncontradoException(String message, Throwable cause) { super(message, cause); }
}
