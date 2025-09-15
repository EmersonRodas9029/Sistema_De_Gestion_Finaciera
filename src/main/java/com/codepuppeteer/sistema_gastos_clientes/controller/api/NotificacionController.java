package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;
import com.codepuppeteer.sistema_gastos_clientes.mapper.NotificacionMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;
    private final NotificacionMapper mapper;

    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> getAll() {
        List<Notificacion> list = service.obtenerTodasLasNotificaciones();
        return ResponseEntity.ok(mapper.toList(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> getById(@PathVariable Long id) {
        Notificacion notificacion = service.obtenerNotificacionPorId(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        return ResponseEntity.ok(mapper.toResponse(notificacion));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponse> create(@RequestBody NotificacionSave dto) {
        Notificacion creado = service.crearNotificacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponse> update(
            @PathVariable Long id,
            @RequestBody NotificacionUpdate dto) {
        Notificacion actualizado = service.actualizarNotificacion(id, dto);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
