package com.ecommerce.amarte.exception;  // Coloca esta clase en el paquete exception

// Esta es la excepción personalizada para cuando el correo electrónico ya existe
public class EmailAlreadyExistsException extends RuntimeException {

    // Constructor que recibe el mensaje de la excepción
    public EmailAlreadyExistsException(String message) {
        super(message);  // Pasa el mensaje a la clase base (RuntimeException)
    }
}
