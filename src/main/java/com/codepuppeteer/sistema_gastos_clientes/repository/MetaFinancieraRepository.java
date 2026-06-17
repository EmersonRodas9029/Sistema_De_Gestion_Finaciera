package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetaFinancieraRepository extends JpaRepository<MetaFinanciera, Long> {
    List<MetaFinanciera> findByClienteId(long clienteId);
}
