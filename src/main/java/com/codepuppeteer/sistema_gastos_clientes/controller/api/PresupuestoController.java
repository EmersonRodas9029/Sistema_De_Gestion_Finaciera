package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import com.codepuppeteer.sistema_gastos_clientes.mapper.PresupuestoMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.PresupuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
@RequiredArgsConstructor
public class PresupuestoController {

    private final PresupuestoService service;
    private final PresupuestoMapper mapper;

    @GetMapping
    public ResponseEntity<List<PresupuestoList>> getAll() {
        List<Presupuesto> presupuestos = service.obtenerTodosLosPresupuestos();
        return ResponseEntity.ok(mapper.toList(presupuestos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoResponse> getById(@PathVariable Long id) {
        Presupuesto presupuesto = service.obtenerPresupuestoPorId(id)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));
        return ResponseEntity.ok(mapper.toResponse(presupuesto));
    }

    @PostMapping
    public ResponseEntity<PresupuestoResponse> create(@RequestBody PresupuestoSave dto) {
        Presupuesto creado = service.crearPresupuestoConRelaciones(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresupuestoResponse> update(
            @PathVariable Long id,
            @RequestBody PresupuestoUpdate dto) {
        Presupuesto actualizado = service.actualizarPresupuestoConRelaciones(id, dto);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarPresupuesto(id);
        return ResponseEntity.noContent().build();
    }
}
