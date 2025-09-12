package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import com.codepuppeteer.sistema_gastos_clientes.mapper.GastoMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;
    private final GastoMapper gastoMapper;

    @GetMapping
    public ResponseEntity<List<GastoList>> getAllGastos() {
        List<Gasto> gastos = gastoService.obtenerTodosLosGastos();
        return ResponseEntity.ok(gastoMapper.toList(gastos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoResponse> getGastoById(@PathVariable Long id) {
        Gasto gasto = gastoService.obtenerGastoPorId(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
        return ResponseEntity.ok(gastoMapper.toResponse(gasto));
    }

    @PostMapping
    public ResponseEntity<GastoResponse> createGasto(@RequestBody GastoSave gastoSave) {
        Gasto creado = gastoService.crearGastoConRelaciones(gastoSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoMapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoResponse> updateGasto(
            @PathVariable Long id,
            @RequestBody GastoUpdate gastoUpdate) {

        Gasto actualizado = gastoService.actualizarGastoConRelaciones(id, gastoUpdate);
        return ResponseEntity.ok(gastoMapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }
}
