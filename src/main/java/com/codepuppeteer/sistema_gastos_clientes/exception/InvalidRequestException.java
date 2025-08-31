package com.codepuppeteer.sistema_gastos_clientes.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
