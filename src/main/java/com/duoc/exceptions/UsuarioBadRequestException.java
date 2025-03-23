package com.duoc.exceptions;

public class UsuarioBadRequestException extends RuntimeException{
    public UsuarioBadRequestException(String mensaje) {
        super(mensaje);
    }
}
