package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;

public record ClienteList(
        Long id,
        String nombreCompleto,
        String telefono,
        String email,
        TipoDocumento tipoDocumento,
        Boolean activo
) {}
