package com.eventosapi.inscricao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;  

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) { super(message); }
    public RegraNegocioException(String message, Throwable cause) { super(message, cause); }
}
