package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;

import java.util.List;
import java.util.Optional;

public interface PresupuestoService {
    Presupuesto crearPresupuestoConRelaciones(PresupuestoSave dto);
    Presupuesto actualizarPresupuestoConRelaciones(long id, PresupuestoUpdate dto);
    void eliminarPresupuesto(long id);
    Optional<Presupuesto> obtenerPresupuestoPorId(long id);
    List<Presupuesto> obtenerTodosLosPresupuestos();
    List<Presupuesto> obtenerPresupuestosPorCliente(long clienteId);
    List<Presupuesto> obtenerPresupuestosPorCategoria(long categoriaId);
}
