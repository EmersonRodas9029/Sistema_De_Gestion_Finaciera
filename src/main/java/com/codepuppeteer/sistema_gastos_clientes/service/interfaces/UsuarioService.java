package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crearUsuario(UsuarioSave usuario);

    UsuarioResponse actualizarUsuario(Long id, UsuarioUpdate usuario);

    void eliminarUsuario(Long id);

    UsuarioResponse obtenerUsuarioPorId(Long id);

    List<UsuarioResponse> obtenerTodosLosUsuarios();

    void bloquearUsuario(Long id);

    void desbloquearUsuario(Long id);
}
