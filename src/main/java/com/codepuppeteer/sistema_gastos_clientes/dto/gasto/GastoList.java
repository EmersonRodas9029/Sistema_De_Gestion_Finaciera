package com.codepuppeteer.sistema_gastos_clientes.dto.gasto;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoPago;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoList(
        Long id,
        Long clienteId,
        Long categoriaId,
        BigDecimal monto,
        LocalDate fecha,
        String descripcion,
        MetodoPago metodoPago,
        Boolean esRecurrente,
        Frecuencia frecuencia,
        Boolean activo
) {}
