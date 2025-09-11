package com.codepuppeteer.sistema_gastos_clientes.dto.usuario;

import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;

public record UsuarioList(
        Long id,
        String username,
        String email,
        Rol rol,
        boolean activo
) {}
