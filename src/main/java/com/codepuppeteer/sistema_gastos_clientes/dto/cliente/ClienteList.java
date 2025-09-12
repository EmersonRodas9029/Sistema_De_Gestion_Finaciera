package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;

public record ClienteList(
        Long id,
        String nombreCompleto,
        String email,
        String telefono,
        TipoDocumento tipoDocumento,
        Boolean activo
) {}
