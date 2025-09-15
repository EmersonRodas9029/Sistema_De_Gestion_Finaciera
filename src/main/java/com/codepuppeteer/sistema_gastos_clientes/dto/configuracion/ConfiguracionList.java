package com.codepuppeteer.sistema_gastos_clientes.dto.configuracion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoConfiguracion;

public record ConfiguracionList(
        Long id,
        Long clienteId,
        String clave,
        String valor,
        TipoConfiguracion tipo
) {}
