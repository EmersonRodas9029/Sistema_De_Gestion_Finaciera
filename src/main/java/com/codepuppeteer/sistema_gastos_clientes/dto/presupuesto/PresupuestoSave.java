package com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PresupuestoSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        Long categoriaId,

        @NotNull(message = "El monto presupuestado es obligatorio")
        @Positive(message = "El monto presupuestado debe ser mayor a cero")
        BigDecimal montoPresupuestado,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe estar entre 1 y 12")
        @Max(value = 12, message = "El mes debe estar entre 1 y 12")
        Integer mes,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año no es válido")
        Integer anio,

        Boolean activo
) {}
