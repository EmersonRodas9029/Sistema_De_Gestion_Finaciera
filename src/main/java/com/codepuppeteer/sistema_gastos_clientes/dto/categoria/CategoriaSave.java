package com.codepuppeteer.sistema_gastos_clientes.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CategoriaSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotBlank(message = "El nombre de la categoría es obligatorio")
        String nombre,

        String descripcion,

        String color,

        String icono,

        BigDecimal presupuestoMensual
) {}
