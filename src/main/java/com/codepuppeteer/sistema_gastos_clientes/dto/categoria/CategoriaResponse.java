package com.codepuppeteer.sistema_gastos_clientes.dto.categoria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion,
        String color,
        String icono,
        BigDecimal presupuestoMensual,
        Boolean activa,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}
