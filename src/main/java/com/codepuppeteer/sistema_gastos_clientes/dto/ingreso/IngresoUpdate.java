package com.codepuppeteer.sistema_gastos_clientes.dto.ingreso;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoRecepcion;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoIngreso;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IngresoUpdate(
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        LocalDate fecha,
        TipoIngreso tipo,
        String fuente,
        String descripcion,
        MetodoRecepcion metodoRecepcion,
        Boolean esRecurrente,
        Frecuencia frecuencia,
        Boolean activo
) {}
