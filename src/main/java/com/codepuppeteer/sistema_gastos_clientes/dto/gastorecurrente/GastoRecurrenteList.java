package com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRecurrenteList(
        Long id,
        BigDecimal monto,
        Frecuencia frecuencia,
        LocalDate fechaInicio,
        Boolean activo
) {}
