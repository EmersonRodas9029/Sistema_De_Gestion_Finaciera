package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteSave(
        @NotBlank(message = "El nombre completo es obligatorio")
        String nombreCompleto,

        String telefono,

        @Email(message = "El email no es válido")
        String email,

        LocalDate fechaNacimiento,

        String direccion,

        String documentoIdentidad,

        TipoDocumento tipoDocumento
) {}
