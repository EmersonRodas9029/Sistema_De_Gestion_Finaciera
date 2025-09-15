package com.codepuppeteer.sistema_gastos_clientes.dto.reporte;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoReporte;
import java.time.LocalDate;

public record ReporteList(
        Long id,
        String nombre,
        TipoReporte tipoReporte,
        LocalDate periodoInicio,
        LocalDate periodoFin
) {}
