package com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRecurrenteUpdate(
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        String descripcion,
        Frecuencia frecuencia,
        LocalDate fechaFin,
        Integer diaMes,
        Integer diaSemana,
        Boolean activo
) {}
