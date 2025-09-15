package com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera;

import com.codepuppeteer.sistema_gastos_clientes.enums.Prioridad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MetaFinancieraResponse(
        Long id,
        Long clienteId,
        String nombre,
        String descripcion,
        BigDecimal montoObjetivo,
        BigDecimal montoActual,
        LocalDate fechaLimite,
        Prioridad prioridad,
        Boolean activa,
        Boolean completada,
        LocalDate fechaCompletada,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}
