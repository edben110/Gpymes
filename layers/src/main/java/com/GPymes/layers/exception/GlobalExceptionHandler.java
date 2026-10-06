package com.GPymes.layers.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: Pyme, Empleado, Gasto o Nomina inexistente
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400: invariantes del dominio (montos negativos, nombre en blanco, cambio de estado repetido, etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return construir(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400: fallos de @Valid en los DTOs de request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return construir(HttpStatus.BAD_REQUEST, mensaje);
    }

    // 400: JSON mal formado o dato de tipo incompatible dentro del body
    // (enum invalido como "categoria": "XYZ", string donde va un numero, UUID corrupto, etc.)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJSONNoLegible(HttpMessageNotReadableException ex) {
        String causa = ex.getMostSpecificCause().getMessage();
        return construir(HttpStatus.BAD_REQUEST,
                "El cuerpo de la peticion no es valido (JSON mal formado o dato de tipo incorrecto): " + causa);
    }

    // 400: UUID o categoria invalidos en la ruta / query params
    // ("/api/pymes/no-es-uuid", "/api/gastos/categoria/XYZ")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        String esperaba = ex.getRequiredType() == null
                ? ""
                : " (se esperaba " + ex.getRequiredType().getSimpleName() + ")";
        return construir(HttpStatus.BAD_REQUEST,
                "El valor '" + ex.getValue() + "' no es valido para el parametro '"
                        + ex.getName() + "'" + esperaba);
    }

    // 409: nombre de pyme o documento de empleado duplicado
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarIntegridad(DataIntegrityViolationException ex) {
        return construir(HttpStatus.CONFLICT, "El registro viola una restriccion de la base de datos (dato duplicado o relacionado)");
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje) {
        ErrorResponse cuerpo = new ErrorResponse(status.value(), status.getReasonPhrase(), mensaje, LocalDateTime.now());
        return ResponseEntity.status(status).body(cuerpo);
    }
}
