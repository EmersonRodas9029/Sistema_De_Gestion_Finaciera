package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.IngresoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<IngresoList>> obtenerPorCliente(@PathVariable long clienteId) {
        return ResponseEntity.ok(ingresoService.obtenerIngresosPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<IngresoResponse> crearIngreso(@Valid @RequestBody IngresoSave dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.crearIngreso(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngresoResponse> actualizarIngreso(@PathVariable long id, @Valid @RequestBody IngresoUpdate dto) {
        return ResponseEntity.ok(ingresoService.actualizarIngreso(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable long id) {
        ingresoService.eliminarIngreso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponse> obtenerIngresoPorId(@PathVariable long id) {
        return ResponseEntity.ok(ingresoService.obtenerIngresoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<IngresoList>> obtenerTodosLosIngresos() {
        return ResponseEntity.ok(ingresoService.obtenerTodosLosIngresos());
    }
}
