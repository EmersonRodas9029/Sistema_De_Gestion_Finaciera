package com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera;

import com.codepuppeteer.sistema_gastos_clientes.enums.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaFinancieraSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotBlank(message = "El nombre de la meta es obligatorio")
        String nombre,

        String descripcion,

        @NotNull(message = "El monto objetivo es obligatorio")
        @Positive(message = "El monto objetivo debe ser mayor a cero")
        BigDecimal montoObjetivo,

        @PositiveOrZero(message = "El monto actual no puede ser negativo")
        BigDecimal montoActual,

        LocalDate fechaLimite,
        Prioridad prioridad,
        Boolean activa,
        Boolean completada,
        LocalDate fechaCompletada
) {}
