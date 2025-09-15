package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import com.codepuppeteer.sistema_gastos_clientes.dto.cuenta.*;
import java.util.List;
import java.util.Optional;

public interface CuentaBancariaService {

    CuentaBancaria crearCuentaConCliente(CuentaBancariaSave dto);

    CuentaBancaria actualizarCuentaConCliente(Long id, CuentaBancariaUpdate dto);

    void eliminarCuenta(Long id);

    Optional<CuentaBancaria> obtenerCuentaPorId(Long id);

    List<CuentaBancaria> obtenerTodasLasCuentas();
}
