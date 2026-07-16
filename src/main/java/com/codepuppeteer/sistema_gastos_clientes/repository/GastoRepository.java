package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findByClienteId(long clienteId);

    List<Gasto> findByCategoriaId(long categoriaId);

    boolean existsByClienteId(long clienteId);
}
