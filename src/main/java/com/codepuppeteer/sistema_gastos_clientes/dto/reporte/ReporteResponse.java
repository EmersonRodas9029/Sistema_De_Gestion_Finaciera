package com.codepuppeteer.sistema_gastos_clientes.dto.reporte;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoReporte;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReporteResponse(
        Long id,
        Long clienteId,
        Long contadorId,
        String nombre,
        TipoReporte tipoReporte,
        LocalDate periodoInicio,
        LocalDate periodoFin,
        String contenido,
        String rutaArchivo,
        LocalDateTime fechaGeneracion
) {}
