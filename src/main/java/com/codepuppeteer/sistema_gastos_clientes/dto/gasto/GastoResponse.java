package com.codepuppeteer.sistema_gastos_clientes.dto.gasto;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoPago;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GastoResponse(
        Long id,
        Long clienteId,
        Long categoriaId,
        BigDecimal monto,
        LocalDate fecha,
        String descripcion,
        MetodoPago metodoPago,
        Boolean esRecurrente,
        Frecuencia frecuencia,
        String adjunto,
        Boolean activo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}