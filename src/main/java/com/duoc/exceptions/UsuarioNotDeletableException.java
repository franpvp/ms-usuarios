package com.duoc.exceptions;

public class UsuarioNotDeletableException extends RuntimeException{
    public UsuarioNotDeletableException(String mensaje) {
        super(mensaje);
    }
}
