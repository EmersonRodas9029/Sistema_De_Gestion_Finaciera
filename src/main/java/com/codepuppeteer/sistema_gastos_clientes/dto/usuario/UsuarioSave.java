package com.codepuppeteer.sistema_gastos_clientes.dto.usuario;

import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioSave(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 255, message = "El username debe tener entre 3 y 255 caracteres")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @Email(message = "Email inválido")
        String email,

        @NotNull(message = "El rol es obligatorio")
        Rol rol,

        // Campos del perfil cliente (solo aplican cuando rol = CLIENTE)
        String nombreCompleto,
        String telefono,
        LocalDate fechaNacimiento,
        String documentoIdentidad,
        TipoDocumento tipoDocumento,
        String direccion
) {}
