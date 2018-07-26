package com.trendyol.webFluxDemo.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleNotFoundException(NotFoundException exception) {
        log.info("Thread {}:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(constructResponse(exception.getMessage())));
    }

    private Map<String, String> constructResponse(String message) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return responseMap;
    }
}
