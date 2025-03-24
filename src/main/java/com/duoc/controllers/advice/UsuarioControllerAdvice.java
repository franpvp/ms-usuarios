package com.duoc.controllers.advice;

import com.duoc.dto.ErrorResponse;
import com.duoc.exceptions.PasswordException;
import com.duoc.exceptions.UsuarioDuplicadoException;
import com.duoc.exceptions.UsuarioNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UsuarioControllerAdvice {

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarUsuarioNoEncontrado(UsuarioNotFoundException ex){
        log.error("Usuario no encontrado: {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UsuarioDuplicadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarUsuarioDuplicado(UsuarioDuplicadoException ex){
        log.error("Nombre de usuario ya se encuentra en uso : {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarPasswordIncorrecta(PasswordException ex){
        log.error("Contraseña incorrecta : {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
