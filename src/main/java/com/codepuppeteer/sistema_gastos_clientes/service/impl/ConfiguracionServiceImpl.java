package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ConfiguracionMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ConfiguracionRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfiguracionServiceImpl implements ConfiguracionService {

    private final ConfiguracionRepository repository;
    private final ClienteRepository clienteRepository;
    private final ConfiguracionMapper mapper;

    @Override
    public Configuracion crearConfiguracion(ConfiguracionSave dto) {
        Configuracion config = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        config.setCliente(cliente);

        return repository.save(config);
    }

    @Override
    public Configuracion actualizarConfiguracion(Long id, ConfiguracionUpdate dto) {
        Configuracion config = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada"));

        mapper.updateFromDto(dto, config);

        return repository.save(config);
    }

    @Override
    public void eliminarConfiguracion(Long id) {
        Configuracion config = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada"));
        repository.delete(config);
    }

    @Override
    public Optional<Configuracion> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Configuracion> obtenerTodas() {
        return repository.findAll();
    }
}
