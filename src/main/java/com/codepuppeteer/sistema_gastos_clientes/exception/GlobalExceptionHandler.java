package com.codepuppeteer.sistema_gastos_clientes.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String,Object>> handleForbidden(ForbiddenException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Forbidden");
        body.put("message", ex.getMessage());
        body.put("status", 403);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("status", 404);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorized(UnauthorizedException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("status", 401);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String,Object>> handleDuplicate(DuplicateResourceException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("status", 409);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,Object>> handleBusiness(BusinessException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("status", 409);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidRequest(InvalidRequestException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("status", 400);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Bad Request");
        body.put("message", message);
        body.put("status", 400);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        Map<String,Object> body = new HashMap<>();
        body.put("error", "Internal Server Error");
        body.put("message", "Ocurrió un error inesperado. Intenta de nuevo más tarde.");
        body.put("status", 500);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
