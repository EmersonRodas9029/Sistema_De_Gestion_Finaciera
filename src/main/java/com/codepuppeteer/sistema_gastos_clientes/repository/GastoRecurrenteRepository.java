package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoRecurrenteRepository extends JpaRepository<GastoRecurrente, Long> {
    List<GastoRecurrente> findByClienteId(Long clienteId);
}
