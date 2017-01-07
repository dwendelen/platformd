package com.github.dwendelen.platformd.infrastructure.spring;

import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class JSR303ViolationExceptionHandler {
    @ExceptionHandler(JSR303ViolationException.class)
    public ResponseEntity handle(JSR303ViolationException e) throws IOException {
        return ResponseEntity.badRequest().body(mapErrors(e));
    }

    private Map<String, String> mapErrors(JSR303ViolationException e) {
        return e.getViolations().stream()
                .collect(Collectors.toMap(cv -> cv.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
}