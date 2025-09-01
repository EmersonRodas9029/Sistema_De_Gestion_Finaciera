package com.codepuppeteer.sistema_gastos_clientes.dto.cliente;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClienteSave(
        @NotNull Long usuarioId,
        @NotBlank String nombreCompleto,
        String telefono,
        @Email String email,
        LocalDate fechaNacimiento,
        String direccion,
        String documentoIdentidad,
        TipoDocumento tipoDocumento
) {}
