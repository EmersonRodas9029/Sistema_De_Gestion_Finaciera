package com.codepuppeteer.sistema_gastos_clientes.dto.cuenta;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CuentaBancariaList(
        Long id,
        String banco,
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        BigDecimal saldoActual,
        Boolean activa
) {}