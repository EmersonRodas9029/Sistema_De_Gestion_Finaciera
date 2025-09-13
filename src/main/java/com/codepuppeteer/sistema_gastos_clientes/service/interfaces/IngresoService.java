package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;

import java.util.List;

public interface IngresoService {

    IngresoResponse crearIngreso(IngresoSave dto);

    IngresoResponse actualizarIngreso(Long id, IngresoUpdate dto);

    void eliminarIngreso(Long id);

    IngresoResponse obtenerIngresoPorId(Long id);

    List<IngresoList> obtenerTodosLosIngresos();
}
