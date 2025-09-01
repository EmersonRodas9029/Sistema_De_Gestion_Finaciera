package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ClienteMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @GetMapping
    public ResponseEntity<List<ClienteList>> getAllClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clienteMapper.toList(clientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return ResponseEntity.ok(clienteMapper.toResponse(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@RequestBody @Valid ClienteSave clienteSave) {
        Cliente creado = clienteService.crearCliente(clienteSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteMapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(
            @PathVariable Long id,
            @RequestBody ClienteUpdate clienteUpdate) {
        Cliente existente = clienteService.obtenerClientePorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        clienteMapper.updateFromDto(clienteUpdate, existente);
        Cliente actualizado = clienteService.actualizarCliente(id, existente);
        return ResponseEntity.ok(clienteMapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
