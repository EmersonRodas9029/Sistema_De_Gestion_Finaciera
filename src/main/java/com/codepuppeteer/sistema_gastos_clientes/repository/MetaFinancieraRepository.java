package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaFinancieraRepository extends JpaRepository<MetaFinanciera, Long> {
    List<MetaFinanciera> findByClienteId(Long clienteId);
}
