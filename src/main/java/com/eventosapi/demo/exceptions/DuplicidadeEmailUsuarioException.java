package com.eventosapi.demo.exceptions;

public class DuplicidadeEmailUsuarioException extends RuntimeException {
    public DuplicidadeEmailUsuarioException(String message) {
        super(message);
    }
}
