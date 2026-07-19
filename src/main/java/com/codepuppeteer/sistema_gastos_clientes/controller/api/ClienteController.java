package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteList;
import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteUpdate;
import com.codepuppeteer.sistema_gastos_clientes.security.UsuarioDetails;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteList>> getAllClientes(
            @org.springframework.web.bind.annotation.RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(clienteService.getAllClientes(activo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody ClienteSave dto, Authentication auth) {
        Long usuarioId = ((UsuarioDetails) auth.getPrincipal()).getUsuarioId();
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.createCliente(dto, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable long id, @Valid @RequestBody ClienteUpdate dto) {
        return ResponseEntity.ok(clienteService.updateCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
