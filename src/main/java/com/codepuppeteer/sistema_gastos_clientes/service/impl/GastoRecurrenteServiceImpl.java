package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.GastoRecurrenteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.CategoriaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRecurrenteRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoRecurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class GastoRecurrenteServiceImpl implements GastoRecurrenteService {

    private final GastoRecurrenteRepository repository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoRecurrenteMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public GastoRecurrenteResponse save(GastoRecurrenteSave dto) {
        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        Long catId = dto.categoriaId();
        Categoria categoria = catId != null
                ? categoriaRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"))
                : null;

        GastoRecurrente entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setCategoria(categoria);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public GastoRecurrenteResponse update(long id, GastoRecurrenteUpdate dto) {
        GastoRecurrente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
        securityUtils.checkOwnership(entity.getCliente());
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }


    @Override
    public void delete(long id) {
        GastoRecurrente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
        securityUtils.checkOwnership(entity.getCliente());
        repository.delete(entity);
    }

    @Override
    public GastoRecurrenteResponse getById(long id) {
        GastoRecurrente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
        securityUtils.checkOwnership(entity.getCliente());
        return mapper.toResponse(entity);
    }

    @Override
    public List<GastoRecurrenteList> getAllByCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        return mapper.toList(repository.findByClienteId(clienteId));
    }
}
