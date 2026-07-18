package com.codepuppeteer.sistema_gastos_clientes.dto.configuracion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoConfiguracion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfiguracionSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotBlank(message = "La clave es obligatoria")
        String clave,

        @NotBlank(message = "El valor es obligatorio")
        String valor,

        @NotNull(message = "El tipo de configuración es obligatorio")
        TipoConfiguracion tipo,

        String descripcion
) {}
