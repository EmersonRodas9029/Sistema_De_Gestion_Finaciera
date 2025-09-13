package com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PresupuestoResponse(
        Long id,
        Long clienteId,
        Long categoriaId,
        BigDecimal montoPresupuestado,
        Integer mes,
        Integer anio,
        Boolean activo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}

