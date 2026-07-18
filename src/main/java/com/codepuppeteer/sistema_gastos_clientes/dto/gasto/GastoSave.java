package com.codepuppeteer.sistema_gastos_clientes.dto.gasto;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        Long categoriaId,

        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        String descripcion,

        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,

        Boolean esRecurrente,
        Frecuencia frecuencia,
        String adjunto,
        Boolean activo
) {}
