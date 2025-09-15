package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.cuenta.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.repository.CuentaBancariaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.CuentaBancariaService;
import com.codepuppeteer.sistema_gastos_clientes.mapper.CuentaBancariaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    private final CuentaBancariaRepository repository;
    private final ClienteRepository clienteRepository;
    private final CuentaBancariaMapper mapper;

    @Override
    public CuentaBancaria crearCuentaConCliente(CuentaBancariaSave dto) {
        if (dto.clienteId() == null) {
            throw new IllegalArgumentException("El clienteId no puede ser null");
        }

        CuentaBancaria cuenta = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cuenta.setCliente(cliente);

        if (cuenta.getSaldoActual() == null) cuenta.setSaldoActual(BigDecimal.ZERO);
        if (cuenta.getActiva() == null) cuenta.setActiva(true);

        return repository.save(cuenta);
    }


    @Override
    public CuentaBancaria actualizarCuentaConCliente(Long id, CuentaBancariaUpdate dto) {
        CuentaBancaria cuenta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        mapper.updateFromDto(dto, cuenta);

        if (dto.clienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
            cuenta.setCliente(cliente);
        }

        if (cuenta.getSaldoActual() == null) cuenta.setSaldoActual(BigDecimal.ZERO);
        if (cuenta.getActiva() == null) cuenta.setActiva(true);

        cuenta.setFechaActualizacionSaldo(java.time.LocalDateTime.now());

        return repository.save(cuenta);
    }

    @Override
    public void eliminarCuenta(Long id) {
        CuentaBancaria cuenta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        repository.delete(cuenta);
    }

    @Override
    public Optional<CuentaBancaria> obtenerCuentaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CuentaBancaria> obtenerTodasLasCuentas() {
        return repository.findAll();
    }
}
