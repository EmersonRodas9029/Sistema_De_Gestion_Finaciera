package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoRecurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos-recurrentes")
@RequiredArgsConstructor
public class GastoRecurrenteController {

    private final GastoRecurrenteService service;

    @PostMapping
    public ResponseEntity<GastoRecurrenteResponse> save(@RequestBody GastoRecurrenteSave dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoRecurrenteResponse> update(@PathVariable Long id, @RequestBody GastoRecurrenteUpdate dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoRecurrenteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<GastoRecurrenteList>> getAllByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.getAllByCliente(clienteId));
    }
}
