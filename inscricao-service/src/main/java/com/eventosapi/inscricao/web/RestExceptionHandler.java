package com.eventosapi.inscricao.web;

import com.eventosapi.inscricao.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.exception.RegraNegocioException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;    

import java.time.OffsetDateTime;
import java.util.Map;


@RestControllerAdvice(basePackages = "com.eventosapi.inscricao.web")
public class RestExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> notFound(EntidadeNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> business(RegraNegocioException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body("UNPROCESSABLE_ENTITY", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> conflict(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body("CONFLICT", "Violação de integridade/duplicidade."));
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> badRequest(Exception ex) {
        return ResponseEntity.badRequest().body(body("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body("INTERNAL_ERROR", "Erro inesperado."));
    }

    private Map<String, Object> body(String code, String message) {
        return Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "code", code,
            "error", message
        );
    }
}
