package com.codepuppeteer.sistema_gastos_clientes.dto.ingreso;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IngresoList(
        Long id,
        BigDecimal monto,
        LocalDate fecha,
        String tipo,
        Boolean activo
) {}
