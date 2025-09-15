package com.codepuppeteer.sistema_gastos_clientes.dto.configuracion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoConfiguracion;
import java.time.LocalDateTime;

public record ConfiguracionResponse(
        Long id,
        Long clienteId,
        String clave,
        String valor,
        TipoConfiguracion tipo,
        String descripcion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}
