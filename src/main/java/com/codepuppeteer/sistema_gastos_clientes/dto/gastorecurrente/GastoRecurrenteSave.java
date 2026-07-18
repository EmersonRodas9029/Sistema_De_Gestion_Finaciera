package com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRecurrenteSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        Long categoriaId,

        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "La frecuencia es obligatoria")
        Frecuencia frecuencia,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio,

        LocalDate fechaFin,
        Integer diaMes,
        Integer diaSemana
) {}
