package com.codepuppeteer.sistema_gastos_clientes.dto.cuenta;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CuentaBancariaSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotBlank(message = "El banco es obligatorio")
        String banco,

        @NotBlank(message = "El número de cuenta es obligatorio")
        String numeroCuenta,

        @NotNull(message = "El tipo de cuenta es obligatorio")
        TipoCuenta tipoCuenta,

        BigDecimal saldoActual,
        Boolean activa
) {}
