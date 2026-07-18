package com.codepuppeteer.sistema_gastos_clientes.controller.api;

import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import com.codepuppeteer.sistema_gastos_clientes.dto.cuenta.*;
import com.codepuppeteer.sistema_gastos_clientes.mapper.CuentaBancariaMapper;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.CuentaBancariaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaBancariaController {

    private final CuentaBancariaService cuentaService;
    private final CuentaBancariaMapper mapper;

    @GetMapping
    public ResponseEntity<List<CuentaBancariaList>> getAllCuentas() {
        List<CuentaBancaria> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(mapper.toList(cuentas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancariaResponse> getCuentaById(@PathVariable long id) {
        CuentaBancaria cuenta = cuentaService.obtenerCuentaPorId(id)
                .orElseThrow(() -> new com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException("Cuenta no encontrada"));
        return ResponseEntity.ok(mapper.toResponse(cuenta));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaBancariaList>> getByCliente(@PathVariable long clienteId) {
        return ResponseEntity.ok(mapper.toList(cuentaService.obtenerCuentasPorCliente(clienteId)));
    }

    @PostMapping
    public ResponseEntity<CuentaBancariaResponse> createCuenta(@Valid @RequestBody CuentaBancariaSave dto) {
        CuentaBancaria creado = cuentaService.crearCuentaConCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaBancariaResponse> updateCuenta(
            @PathVariable long id,
            @RequestBody CuentaBancariaUpdate dto) {


        CuentaBancaria actualizado = cuentaService.actualizarCuentaConCliente(id, dto);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable long id) {

        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

}
