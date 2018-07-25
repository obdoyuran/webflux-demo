package com.trendyol.webFluxDemo.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<String>> handleNotFoundException(NotFoundException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }
}
