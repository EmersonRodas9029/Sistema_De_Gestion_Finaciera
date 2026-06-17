package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crearUsuario(UsuarioSave usuario);

    UsuarioResponse actualizarUsuario(long id, UsuarioUpdate usuario);

    void eliminarUsuario(long id);

    UsuarioResponse obtenerUsuarioPorId(long id);

    List<UsuarioResponse> obtenerTodosLosUsuarios();

    void bloquearUsuario(long id);

    void desbloquearUsuario(long id);
}
