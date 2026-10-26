package com.verdegreen.exception;

/** No existe el carrito o el envío pedido. Responde 404. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
