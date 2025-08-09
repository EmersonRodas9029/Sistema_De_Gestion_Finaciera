package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.model.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.model.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findByCategoria(Categoria categoria);

    List<Gasto> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
