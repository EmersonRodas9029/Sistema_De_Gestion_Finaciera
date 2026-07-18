package com.codepuppeteer.sistema_gastos_clientes.dto.gasto;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoPago;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoUpdate(
        Long categoriaId,

        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal monto,

        LocalDate fecha,
        String descripcion,
        MetodoPago metodoPago,
        Boolean esRecurrente,
        Frecuencia frecuencia,
        String adjunto,
        Boolean activo
) {}
