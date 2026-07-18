package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.CambiarPasswordRequest;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody UsuarioSave usuarioSave) {
        UsuarioResponse usuario = usuarioService.crearUsuario(usuarioSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(
            @PathVariable long id,
            @Valid @RequestBody UsuarioUpdate usuarioUpdate) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioUpdate));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> cambiarPassword(
            @PathVariable long id,
            @Valid @RequestBody CambiarPasswordRequest request) {
        usuarioService.cambiarPassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable long id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deleteUsuarioPermanente(@PathVariable long id) {
        usuarioService.eliminarUsuarioPermanente(id);
        return ResponseEntity.noContent().build();
    }
}
