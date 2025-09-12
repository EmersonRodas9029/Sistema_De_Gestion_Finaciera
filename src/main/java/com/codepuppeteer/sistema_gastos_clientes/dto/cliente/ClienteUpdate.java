package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;

import java.time.LocalDate;

public record ClienteUpdate(
        String nombreCompleto,
        String telefono,
        String email,
        LocalDate fechaNacimiento,
        String direccion,
        String documentoIdentidad,
        TipoDocumento tipoDocumento,
        Boolean activo
) {}
