package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.model.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.model.entity.GastoRecurrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRecurrenteRepository extends JpaRepository<GastoRecurrente, Long> {

    List<GastoRecurrente> findByCategoria(Categoria categoria);
}
