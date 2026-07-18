package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;
import com.codepuppeteer.sistema_gastos_clientes.exception.DuplicateResourceException;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ConfiguracionMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ConfiguracionRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfiguracionServiceImpl implements ConfiguracionService {

    private final ConfiguracionRepository repository;
    private final ClienteRepository clienteRepository;
    private final ConfiguracionMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public Configuracion crearConfiguracion(ConfiguracionSave dto) {
        Configuracion config = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.clienteId()));
        securityUtils.checkOwnership(cliente);

        if (repository.existsByClienteIdAndClave(dto.clienteId(), dto.clave())) {
            throw new DuplicateResourceException(
                    "Ya existe una configuración con clave '" + dto.clave() + "' para este cliente");
        }

        config.setCliente(cliente);

        return repository.save(config);
    }

    @Override
    public Configuracion actualizarConfiguracion(long id, ConfiguracionUpdate dto) {
        Configuracion config = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración no encontrada con id: " + id));
        securityUtils.checkOwnership(config.getCliente());

        if (dto.clave() != null && !dto.clave().equals(config.getClave())
                && repository.existsByClienteIdAndClave(config.getCliente().getId(), dto.clave())) {
            throw new DuplicateResourceException(
                    "Ya existe una configuración con clave '" + dto.clave() + "' para este cliente");
        }

        mapper.updateFromDto(dto, config);

        return repository.save(config);
    }

    @Override
    public void eliminarConfiguracion(long id) {
        Configuracion config = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración no encontrada con id: " + id));
        securityUtils.checkOwnership(config.getCliente());
        repository.delete(config);
    }

    @Override
    public Optional<Configuracion> obtenerPorId(long id) {
        Optional<Configuracion> config = repository.findById(id);
        config.ifPresent(c -> securityUtils.checkOwnership(c.getCliente()));
        return config;
    }

    @Override
    public List<Configuracion> obtenerTodas() {
        List<Configuracion> configuraciones = repository.findAll();
        if (!securityUtils.isContador()) {
            Long usuarioId = securityUtils.getCurrentUser().getUsuarioId();
            return configuraciones.stream()
                    .filter(c -> c.getCliente() != null && c.getCliente().getUsuario().getId().equals(usuarioId))
                    .toList();
        }
        return configuraciones;
    }
}
