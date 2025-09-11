package com.codepuppeteer.sistema_gastos_clientes.dto.usuario;

import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        Rol rol
) {}
