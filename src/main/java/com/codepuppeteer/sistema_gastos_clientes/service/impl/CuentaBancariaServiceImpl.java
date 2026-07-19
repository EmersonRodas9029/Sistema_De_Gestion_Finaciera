package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.cuenta.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.repository.CuentaBancariaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.CuentaBancariaService;
import com.codepuppeteer.sistema_gastos_clientes.mapper.CuentaBancariaMapper;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    private final CuentaBancariaRepository repository;
    private final ClienteRepository clienteRepository;
    private final CuentaBancariaMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public CuentaBancaria crearCuentaConCliente(CuentaBancariaSave dto) {
        if (dto.clienteId() == null) {
            throw new IllegalArgumentException("El clienteId no puede ser null");
        }

        CuentaBancaria cuenta = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        cuenta.setCliente(cliente);

        if (cuenta.getSaldoActual() == null) cuenta.setSaldoActual(BigDecimal.ZERO);
        if (cuenta.getActiva() == null) cuenta.setActiva(true);

        return repository.save(cuenta);
    }


    @Override
    public CuentaBancaria actualizarCuentaConCliente(long id, CuentaBancariaUpdate dto) {
        CuentaBancaria cuenta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        securityUtils.checkOwnership(cuenta.getCliente());

        mapper.updateFromDto(dto, cuenta);

        Long clienteId = dto.clienteId();
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
            securityUtils.checkOwnership(cliente);
            cuenta.setCliente(cliente);
        }

        if (cuenta.getSaldoActual() == null) cuenta.setSaldoActual(BigDecimal.ZERO);
        if (cuenta.getActiva() == null) cuenta.setActiva(true);

        cuenta.setFechaActualizacionSaldo(java.time.LocalDateTime.now());

        return repository.save(cuenta);
    }

    @Override
    public void eliminarCuenta(long id) {
        CuentaBancaria cuenta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        securityUtils.checkOwnership(cuenta.getCliente());
        repository.delete(cuenta);
    }

    @Override
    public Optional<CuentaBancaria> obtenerCuentaPorId(long id) {
        Optional<CuentaBancaria> cuenta = repository.findById(id);
        cuenta.ifPresent(c -> securityUtils.checkOwnership(c.getCliente()));
        return cuenta;
    }

    @Override
    public List<CuentaBancaria> obtenerTodasLasCuentas() {
        if (!securityUtils.isContador()) {
            return repository.findByCliente_Usuario_Id(securityUtils.getCurrentUser().getUsuarioId());
        }
        return repository.findAll();
    }

    @Override
    public List<CuentaBancaria> obtenerCuentasPorCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        return repository.findByClienteId(clienteId);
    }
}
