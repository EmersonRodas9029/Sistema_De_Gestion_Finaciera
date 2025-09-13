package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;

import java.util.List;
import java.util.Optional;

public interface PresupuestoService {
    Presupuesto crearPresupuestoConRelaciones(PresupuestoSave dto);
    Presupuesto actualizarPresupuestoConRelaciones(Long id, PresupuestoUpdate dto);
    void eliminarPresupuesto(Long id);
    Optional<Presupuesto> obtenerPresupuestoPorId(Long id);
    List<Presupuesto> obtenerTodosLosPresupuestos();
    List<Presupuesto> obtenerPresupuestosPorCliente(Long clienteId);
    List<Presupuesto> obtenerPresupuestosPorCategoria(Long categoriaId);
}
