package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClienteSave(
        @NotNull(message = "El usuario es obligatorio")
        Long usuarioId,

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
