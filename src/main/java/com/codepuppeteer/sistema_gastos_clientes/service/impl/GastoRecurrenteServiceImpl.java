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
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoRecurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoRecurrenteServiceImpl implements GastoRecurrenteService {

    private final GastoRecurrenteRepository repository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoRecurrenteMapper mapper;

    @Override
    @Transactional
    public GastoRecurrenteResponse save(GastoRecurrenteSave dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Categoria categoria = dto.categoriaId() != null
                ? categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"))
                : null;

        GastoRecurrente entity = mapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setCategoria(categoria);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public GastoRecurrenteResponse update(Long id, GastoRecurrenteUpdate dto) {
        GastoRecurrente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        GastoRecurrente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
        repository.delete(entity);
    }

    @Override
    public GastoRecurrenteResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto recurrente no encontrado"));
    }

    @Override
    public List<GastoRecurrenteList> getAllByCliente(Long clienteId) {
        return mapper.toList(repository.findByClienteId(clienteId));
    }
}
