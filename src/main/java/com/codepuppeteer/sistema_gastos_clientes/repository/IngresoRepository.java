package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.model.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.model.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    List<Ingreso> findByCliente(Cliente cliente);

    List<Ingreso> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
