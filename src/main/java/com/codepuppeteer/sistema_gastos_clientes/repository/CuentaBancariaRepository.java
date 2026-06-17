package com.codepuppeteer.sistema_gastos_clientes.repository;

import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
    List<CuentaBancaria> findByClienteId(long clienteId);
}
