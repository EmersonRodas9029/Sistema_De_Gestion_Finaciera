package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {
    List<Configuracion> findByClienteId(long clienteId);
    boolean existsByClienteIdAndClave(Long clienteId, String clave);
}
