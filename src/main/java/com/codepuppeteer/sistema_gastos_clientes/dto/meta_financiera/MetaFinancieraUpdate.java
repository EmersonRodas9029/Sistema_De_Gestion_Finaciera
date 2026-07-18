package com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera;

import com.codepuppeteer.sistema_gastos_clientes.enums.Prioridad;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaFinancieraUpdate(
        String nombre,
        String descripcion,

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
