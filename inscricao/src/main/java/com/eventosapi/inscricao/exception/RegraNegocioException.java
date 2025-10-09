package com.eventosapi.inscricao.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) { super(message); }
    public RegraNegocioException(String message, Throwable cause) { super(message, cause); }
}
