package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ReporteMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService service;
    private final ReporteMapper mapper;

    @GetMapping
    public ResponseEntity<List<ReporteList>> getAllReportes() {
        List<Reporte> reportes = service.obtenerTodosLosReportes();
        return ResponseEntity.ok(mapper.toList(reportes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> getReporteById(@PathVariable Long id) {
        Reporte reporte = service.obtenerReportePorId(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return ResponseEntity.ok(mapper.toResponse(reporte));
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> createReporte(@RequestBody ReporteSave dto) {
        Reporte creado = service.crearReporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> updateReporte(
            @PathVariable Long id,
            @RequestBody ReporteUpdate dto) {
        Reporte actualizado = service.actualizarReporte(id, dto);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        service.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
