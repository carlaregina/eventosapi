package com.eventosapi.inscricao.exception;

import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntidadeNaoEncontradoException.class)
  public ResponseEntity<?> notFound(EntidadeNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(err("NOT_FOUND", ex.getMessage()));
  }

  @ExceptionHandler(RegraNegocioException.class)
  public ResponseEntity<?> business(RegraNegocioException ex) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(err("BUSINESS_RULE", ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> badRequest(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(err("BAD_REQUEST", ex.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> generic(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(err("INTERNAL_ERROR", "Erro inesperado."));
  }

  private Map<String,Object> err(String code, String msg) {
    return Map.of("timestamp", OffsetDateTime.now().toString(), "code", code, "error", msg);
  }

}
