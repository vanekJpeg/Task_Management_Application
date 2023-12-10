package ru.vanek.task_management_application.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vanek.task_management_application.dtos.responses.ExceptionResponse;
import ru.vanek.task_management_application.dtos.responses.ValidationErrorResponse;
import ru.vanek.task_management_application.dtos.responses.Violation;
import ru.vanek.task_management_application.exceptions.AuthException;
import ru.vanek.task_management_application.exceptions.NotEnoughRulesException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionsController {
//    @ExceptionHandler
//    public ResponseEntity<ExceptionResponse> handleException(Exception e){
//        return new ResponseEntity<>(new ExceptionResponse("Неизвестная ошибка",new Date()),HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),new Date()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e){
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),new Date()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNotEnoughRulesException(NotEnoughRulesException e){
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),new Date()),HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ValidationErrorResponse(violations),HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthException e){
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),new Date()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(new ExceptionResponse("Неправильный логин или пароль",new Date()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(new ExceptionResponse("Время жизни токена вышло",new Date()),HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }
}
