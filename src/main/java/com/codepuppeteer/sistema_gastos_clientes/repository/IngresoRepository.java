package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByClienteId(long clienteId);
    List<Ingreso> findByCliente_Usuario_Id(long usuarioId);
    boolean existsByClienteId(long clienteId);
}
