package com.codepuppeteer.sistema_gastos_clientes.dto.usuario;


import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String username,
        String email,
        Rol rol,
        boolean activo,
        LocalDateTime ultimoAcceso,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {}
