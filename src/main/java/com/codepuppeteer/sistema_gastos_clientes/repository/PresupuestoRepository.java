package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByClienteId(long clienteId);
    List<Presupuesto> findByCliente_Usuario_Id(long usuarioId);
    List<Presupuesto> findByCategoriaId(long categoriaId);
    List<Presupuesto> findByCategoriaIdAndCliente_Usuario_Id(long categoriaId, long usuarioId);
    boolean existsByClienteId(long clienteId);
}
