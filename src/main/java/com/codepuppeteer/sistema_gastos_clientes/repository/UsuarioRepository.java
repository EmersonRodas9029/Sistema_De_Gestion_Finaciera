package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    long countByContadorId(Long contadorId);

    // Usado para que un CONTADOR vea su propia cuenta más los usuarios que él mismo afilió,
    // sin exponer otros contadores ni sus usuarios.
    List<Usuario> findByIdOrContadorId(Long id, Long contadorId);
}
