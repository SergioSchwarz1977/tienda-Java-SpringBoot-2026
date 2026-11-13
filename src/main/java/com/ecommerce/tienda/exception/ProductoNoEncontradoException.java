package com.ecommerce.tienda.exception;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String mensaje){
        super(mensaje);
    }

}
