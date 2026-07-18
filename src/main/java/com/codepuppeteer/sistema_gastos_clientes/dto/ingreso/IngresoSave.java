package com.codepuppeteer.sistema_gastos_clientes.dto.ingreso;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoRecepcion;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoIngreso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IngresoSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotNull(message = "El tipo de ingreso es obligatorio")
        TipoIngreso tipo,

        String fuente,
        String descripcion,
        MetodoRecepcion metodoRecepcion,
        Boolean esRecurrente,
        Frecuencia frecuencia
) {}
