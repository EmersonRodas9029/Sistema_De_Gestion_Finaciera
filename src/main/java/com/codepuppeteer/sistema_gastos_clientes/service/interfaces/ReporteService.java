package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;

import java.util.List;
import java.util.Optional;

public interface ReporteService {

    Reporte crearReporte(ReporteSave dto);

    Reporte actualizarReporte(long id, ReporteUpdate dto);

    void eliminarReporte(long id);

    Optional<Reporte> obtenerReportePorId(long id);

    List<Reporte> obtenerTodosLosReportes();
}
