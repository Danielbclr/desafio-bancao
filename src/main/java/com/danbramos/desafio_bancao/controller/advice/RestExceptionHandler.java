package com.danbramos.desafio_bancao.controller.advice;

import com.danbramos.desafio_bancao.exception.UnprocessableEntityException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
@Hidden
public class RestExceptionHandler {
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Map<String, String>> handleUnprocessableEntity(UnprocessableEntityException ex) {
        Map<String, String> errorResponse = Map.of("error", ex.getMessage());
        log.error("Unprocessable entity error: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
