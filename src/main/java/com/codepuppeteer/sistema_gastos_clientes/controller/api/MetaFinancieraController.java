package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import com.codepuppeteer.sistema_gastos_clientes.mapper.MetaFinancieraMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.MetaFinancieraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaFinancieraController {

    private final MetaFinancieraService metaFinancieraService;
    private final MetaFinancieraMapper metaFinancieraMapper;

    @GetMapping
    public ResponseEntity<List<MetaFinancieraList>> getAll() {
        List<MetaFinanciera> metas = metaFinancieraService.obtenerTodasLasMetas();
        return ResponseEntity.ok(metaFinancieraMapper.toList(metas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaFinancieraResponse> getById(@PathVariable Long id) {
        MetaFinanciera meta = metaFinancieraService.obtenerMetaPorId(id)
                .orElseThrow(() -> new RuntimeException("Meta financiera no encontrada"));
        return ResponseEntity.ok(metaFinancieraMapper.toResponse(meta));
    }

    @PostMapping
    public ResponseEntity<MetaFinancieraResponse> create(@RequestBody MetaFinancieraSave dto) {
        MetaFinanciera creada = metaFinancieraService.crearMeta(metaFinancieraMapper.toEntity(dto), dto.clienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(metaFinancieraMapper.toResponse(creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaFinancieraResponse> update(@PathVariable Long id, @RequestBody MetaFinancieraUpdate dto) {
        MetaFinanciera existente = metaFinancieraService.obtenerMetaPorId(id)
                .orElseThrow(() -> new RuntimeException("Meta financiera no encontrada"));

        metaFinancieraMapper.updateFromDto(dto, existente);
        MetaFinanciera actualizada = metaFinancieraService.actualizarMeta(id, existente);

        return ResponseEntity.ok(metaFinancieraMapper.toResponse(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metaFinancieraService.eliminarMeta(id);
        return ResponseEntity.noContent().build();
    }
}
