package com.codepuppeteer.sistema_gastos_clientes.dto.usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LimiteUsuariosRequest(
        @NotNull(message = "El límite es obligatorio")
        @Min(value = 0, message = "El límite no puede ser negativo")
        Integer limite
) {}
