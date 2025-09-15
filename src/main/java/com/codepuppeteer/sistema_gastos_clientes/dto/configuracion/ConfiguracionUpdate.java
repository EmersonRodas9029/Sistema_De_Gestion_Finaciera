package com.codepuppeteer.sistema_gastos_clientes.dto.configuracion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoConfiguracion;

public record ConfiguracionUpdate(
        String clave,
        String valor,
        TipoConfiguracion tipo,
        String descripcion
) {}
