package com.oauth2security.exceptions;

import com.oauth2security.exceptions.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/20/23
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException ex,
                                                           final HttpServletRequest request) {
        return buildException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<?> handleEntityNotFoundException(final EntityAlreadyExistException ex,
                                                           final HttpServletRequest request) {
        return buildException(ex, request, HttpStatus.CONFLICT);
    }

    private ResponseEntity<?> buildException(Exception ex, HttpServletRequest request, HttpStatus status) {
        ExceptionResponse apiException = new ExceptionResponse().builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .uri(request.getRequestURI())
                .code(status.value())
                .build();
        return new ResponseEntity<>(apiException, status);
    }
}
