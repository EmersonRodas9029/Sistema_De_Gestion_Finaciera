package com.codepuppeteer.sistema_gastos_clientes.dto.categoria;

import java.math.BigDecimal;

public record CategoriaUpdate(
        String nombre,
        String descripcion,
        String color,
        String icono,
        BigDecimal presupuestoMensual,
        Boolean activa
) {}
