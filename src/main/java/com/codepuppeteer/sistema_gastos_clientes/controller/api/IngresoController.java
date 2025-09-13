package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.IngresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;

    @PostMapping
    public ResponseEntity<IngresoResponse> crearIngreso(@RequestBody IngresoSave dto) {
        return ResponseEntity.ok(ingresoService.crearIngreso(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngresoResponse> actualizarIngreso(@PathVariable Long id, @RequestBody IngresoUpdate dto) {
        return ResponseEntity.ok(ingresoService.actualizarIngreso(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable Long id) {
        ingresoService.eliminarIngreso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponse> obtenerIngresoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingresoService.obtenerIngresoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<IngresoList>> obtenerTodosLosIngresos() {
        return ResponseEntity.ok(ingresoService.obtenerTodosLosIngresos());
    }
}
