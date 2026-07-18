package com.codepuppeteer.sistema_gastos_clientes.dto.reporte;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReporteSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        Long contadorId,

        @NotBlank(message = "El nombre del reporte es obligatorio")
        String nombre,

        @NotNull(message = "El tipo de reporte es obligatorio")
        TipoReporte tipoReporte,

        @NotNull(message = "El período de inicio es obligatorio")
        LocalDate periodoInicio,

        @NotNull(message = "El período de fin es obligatorio")
        LocalDate periodoFin,

        String contenido,
        String rutaArchivo
) {}
