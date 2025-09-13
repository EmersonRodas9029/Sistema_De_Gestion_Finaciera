package com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto;

import java.math.BigDecimal;

public record PresupuestoSave(
        Long clienteId,
        Long categoriaId,
        BigDecimal montoPresupuestado,
        Integer mes,
        Integer anio,
        Boolean activo
) {}