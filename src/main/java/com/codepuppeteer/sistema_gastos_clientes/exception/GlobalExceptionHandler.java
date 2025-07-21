package com.codepuppeteer.sistema_gastos_clientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<?> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex, WebRequest request) {
    return generarRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(ValidacionException.class)
  public ResponseEntity<?> manejarValidacion(ValidacionException ex, WebRequest request) {
    return generarRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(AccesoDenegadoException.class)
  public ResponseEntity<?> manejarAccesoDenegado(AccesoDenegadoException ex, WebRequest request) {
    return generarRespuesta(HttpStatus.FORBIDDEN, ex.getMessage(), request);
  }

  @ExceptionHandler(OperacionNoPermitidaException.class)
  public ResponseEntity<?> manejarOperacionNoPermitida(OperacionNoPermitidaException ex, WebRequest request) {
    return generarRespuesta(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), request);
  }

  @ExceptionHandler(ErrorAutenticacionException.class)
  public ResponseEntity<?> manejarErrorAutenticacion(ErrorAutenticacionException ex, WebRequest request) {
    return generarRespuesta(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> manejarExcepcionGeneral(Exception ex, WebRequest request) {
    return generarRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado", request);
  }

  private ResponseEntity<Map<String, Object>> generarRespuesta(HttpStatus status, String mensaje, WebRequest request) {
    Map<String, Object> cuerpo = new HashMap<>();
    cuerpo.put("timestamp", LocalDateTime.now());
    cuerpo.put("estado", status.value());
    cuerpo.put("error", status.getReasonPhrase());
    cuerpo.put("mensaje", mensaje);
    cuerpo.put("ruta", request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(cuerpo, status);
  }
}
