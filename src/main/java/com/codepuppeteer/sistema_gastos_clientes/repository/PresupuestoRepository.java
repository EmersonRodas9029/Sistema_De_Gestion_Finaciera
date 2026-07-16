package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByClienteId(long clienteId);
    List<Presupuesto> findByCategoriaId(long categoriaId);
    boolean existsByClienteId(long clienteId);
}
