package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import java.util.List;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.CambiarPasswordRequest;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;

public interface UsuarioService {

    UsuarioResponse crearUsuario(UsuarioSave usuario);

    UsuarioResponse actualizarUsuario(long id, UsuarioUpdate usuario);

    void cambiarPassword(long id, CambiarPasswordRequest request);

    void eliminarUsuario(long id);

    void activarUsuario(long id);

    void eliminarUsuarioPermanente(long id);

    UsuarioResponse obtenerUsuarioPorId(long id);

    List<UsuarioResponse> obtenerTodosLosUsuarios();

    void bloquearUsuario(long id);

    void desbloquearUsuario(long id);

    void actualizarLimiteUsuarios(long id, int limite);
}
