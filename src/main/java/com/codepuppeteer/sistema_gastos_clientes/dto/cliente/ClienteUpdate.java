package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record ClienteUpdate(
        String nombreCompleto,
        String telefono,

        @Email(message = "El email no es válido")
        String email,

        LocalDate fechaNacimiento,
        String direccion,
        String documentoIdentidad,
        TipoDocumento tipoDocumento,
        Boolean activo
) {}
