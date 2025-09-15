package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ConfiguracionMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuraciones")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionService service;
    private final ConfiguracionMapper mapper;

    @GetMapping
    public ResponseEntity<List<ConfiguracionList>> getAll() {
        List<Configuracion> configs = service.obtenerTodas();
        return ResponseEntity.ok(mapper.toList(configs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracionResponse> getById(@PathVariable Long id) {
        Configuracion config = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada"));
        return ResponseEntity.ok(mapper.toResponse(config));
    }

    @PostMapping
    public ResponseEntity<ConfiguracionResponse> create(@RequestBody ConfiguracionSave dto) {
        Configuracion creado = service.crearConfiguracion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionResponse> update(
            @PathVariable Long id,
            @RequestBody ConfiguracionUpdate dto) {
        Configuracion actualizado = service.actualizarConfiguracion(id, dto);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarConfiguracion(id);
        return ResponseEntity.noContent().build();
    }
}
