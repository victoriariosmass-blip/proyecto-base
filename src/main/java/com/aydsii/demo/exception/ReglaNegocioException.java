package com.aydsii.demo.exception;

/** Se viola una regla de negocio (carrito vacío, límite, stock). Responde 400. */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
