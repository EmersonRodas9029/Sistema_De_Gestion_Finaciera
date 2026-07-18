package com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PresupuestoUpdate(
        Long categoriaId,

        @Positive(message = "El monto presupuestado debe ser mayor a cero")
        BigDecimal montoPresupuestado,

        @Min(value = 1, message = "El mes debe estar entre 1 y 12")
        @Max(value = 12, message = "El mes debe estar entre 1 y 12")
        Integer mes,

        @Min(value = 2000, message = "El año no es válido")
        Integer anio,

        Boolean activo
) {}
