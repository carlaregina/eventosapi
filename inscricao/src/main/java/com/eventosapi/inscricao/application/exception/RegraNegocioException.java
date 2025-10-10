package com.eventosapi.inscricao.application.exception;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) { super(message); }
    public RegraNegocioException(String message, Throwable cause) { super(message, cause); }
}
