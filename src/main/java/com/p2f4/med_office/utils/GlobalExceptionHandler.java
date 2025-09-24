package com.p2f4.med_office.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // BusinessException and its subclasses
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ProblemDetail> handleBusiness(BusinessException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
    pd.setTitle("Business rule violation");
    pd.setProperty("code", ex.getCode());
    pd.setProperty("path", req.getRequestURI());
    return ResponseEntity.status(ex.getStatus()).body(pd);
  }

  // Validation errors
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
    pd.setTitle("Invalid request");
    pd.setProperty("path", req.getRequestURI());
    pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
      .map(fe -> Map.of("field", fe.getField(), "message", "Se deben especificar todos los campos requeridos"))
      .toList());
    return ResponseEntity.badRequest().body(pd);
  }

  // Para ResponseStatusException (si las usas en algún lado)
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ProblemDetail> handleRSE(ResponseStatusException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), ex.getReason());
    pd.setTitle("Request error");
    pd.setProperty("path", req.getRequestURI());
    return ResponseEntity.status(ex.getStatusCode()).body(pd);
  }

  // Fallback for unexpected exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    pd.setTitle("Internal Server Error");
    pd.setProperty("path", req.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
  }
}
