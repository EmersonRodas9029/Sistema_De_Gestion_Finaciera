package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    Usuario actualizarUsuario(Long id, Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> obtenerUsuarioPorId(Long id);

    Optional<Usuario> obtenerUsuarioPorUsername(String username);

    List<Usuario> obtenerTodosLosUsuarios();

    void bloquearUsuario(Long id);

    void desbloquearUsuario(Long id);
}
