package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ClienteService;
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

    @GetMapping
    public ResponseEntity<List<ClienteList>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@RequestBody ClienteSave dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.createCliente(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Long id, @RequestBody ClienteUpdate dto) {
        return ResponseEntity.ok(clienteService.updateCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
