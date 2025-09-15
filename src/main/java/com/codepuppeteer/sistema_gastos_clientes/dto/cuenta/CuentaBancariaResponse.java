package com.codepuppeteer.sistema_gastos_clientes.dto.cuenta;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CuentaBancariaResponse(
        Long id,
        Long clienteId,
        String banco,
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        BigDecimal saldoActual,
        LocalDateTime fechaActualizacionSaldo,
        Boolean activa,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}
