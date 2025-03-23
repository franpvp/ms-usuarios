package com.duoc.exceptions;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(String mensaje){
        super(mensaje);
    }
}
