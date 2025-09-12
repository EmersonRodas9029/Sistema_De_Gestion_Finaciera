package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.GastoSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.GastoUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;

import java.util.List;
import java.util.Optional;

public interface GastoService {

    Gasto crearGastoConRelaciones(GastoSave gastoSave);

    Gasto actualizarGastoConRelaciones(Long id, GastoUpdate gastoUpdate);

    void eliminarGasto(Long id);

    Optional<Gasto> obtenerGastoPorId(Long id);

    List<Gasto> obtenerTodosLosGastos();
}
