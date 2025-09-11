package com.codepuppeteer.sistema_gastos_clientes.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
