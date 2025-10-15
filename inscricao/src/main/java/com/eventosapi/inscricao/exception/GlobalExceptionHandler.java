package com.eventosapi.inscricao.exception;

import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eventosapi.inscricao.application.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.application.exception.RegraNegocioException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntidadeNaoEncontradoException.class)
  public ResponseEntity<?> notFound(EntidadeNaoEncontradoException ex) {
    log.error("Erro de entidade não encontrada: ", ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(err("NOT_FOUND", ex.getMessage()));
  }

  @ExceptionHandler(RegraNegocioException.class)
  public ResponseEntity<?> business(RegraNegocioException ex) {
    log.error("Erro de regra de negócio: ", ex);
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(err("BUSINESS_RULE", ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> badRequest(IllegalArgumentException ex) {
    log.error("Erro de argumento ilegal: ", ex);
    return ResponseEntity.badRequest().body(err("BAD_REQUEST", ex.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> generic(RuntimeException ex) {
    log.error("Erro interno: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(err("INTERNAL_ERROR", "Erro inesperado."));
  }

  private Map<String, Object> err(String code, String msg) {
    return Map.of("timestamp", OffsetDateTime.now().toString(), "code", code, "error", msg);
  }

}
