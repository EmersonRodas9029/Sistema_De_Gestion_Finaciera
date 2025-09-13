package com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto;

import java.math.BigDecimal;

public record PresupuestoList(
        Long id,
        BigDecimal montoPresupuestado,
        Integer mes,
        Integer anio,
        Boolean activo
) {}