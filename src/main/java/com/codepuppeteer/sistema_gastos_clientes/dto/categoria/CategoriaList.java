package com.codepuppeteer.sistema_gastos_clientes.dto.categoria;

import java.math.BigDecimal;

public record CategoriaList(
        Long id,
        String nombre,
        BigDecimal presupuestoMensual,
        Boolean activa
) {}
