package br.com.gabrieudev.picpay.infrastructure.web;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gabrieudev.picpay.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.picpay.application.exceptions.InvalidOperationException;
import br.com.gabrieudev.picpay.application.exceptions.StandardException;
import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardException> handleUserNotFoundException(EntityNotFoundException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<StandardException> handleInvalidOperationException(InvalidOperationException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StandardException standardException = new StandardException(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardException> handleFeignException(FeignException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
