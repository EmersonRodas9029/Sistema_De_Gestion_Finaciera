package com.codepuppeteer.sistema_gastos_clientes.dto.cuenta;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;

import java.math.BigDecimal;

public record CuentaBancariaUpdate(
        Long clienteId,
        String banco,
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        BigDecimal saldoActual,
        Boolean activa
) {}

