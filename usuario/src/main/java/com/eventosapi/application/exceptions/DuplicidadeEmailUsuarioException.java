package com.eventosapi.application.exceptions;

public class DuplicidadeEmailUsuarioException extends RuntimeException {
    public DuplicidadeEmailUsuarioException(String message) {
        super(message);
    }
}
