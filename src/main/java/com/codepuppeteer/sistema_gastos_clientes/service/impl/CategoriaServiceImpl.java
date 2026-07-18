package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.CategoriaMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.CategoriaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaMapper categoriaMapper;
    private final SecurityUtils securityUtils;

    // Categoria pertenece siempre a un Cliente (cliente_id NOT NULL), no es global/compartida,
    // por lo que se aplica el mismo control de propiedad que en el resto de entidades.
    @Override
    public CategoriaResponse create(CategoriaSave dto) {
        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.clienteId()));
        securityUtils.checkOwnership(cliente);

        if (categoriaRepository.existsByClienteIdAndNombre(Objects.requireNonNull(cliente.getId()), dto.nombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre para este cliente");
        }

        Categoria categoria = categoriaMapper.toEntity(dto);
        categoria.setCliente(cliente);

        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponse update(long id, CategoriaUpdate dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        securityUtils.checkOwnership(categoria.getCliente());

        categoriaMapper.updateFromDto(dto, categoria);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    public void delete(long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        securityUtils.checkOwnership(categoria.getCliente());
        categoriaRepository.delete(categoria);
    }

    @Override
    public CategoriaResponse getById(long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        securityUtils.checkOwnership(categoria.getCliente());
        return categoriaMapper.toResponse(categoria);
    }

    @Override
    public List<CategoriaList> getAllByCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
        securityUtils.checkOwnership(cliente);
        return categoriaMapper.toList(categoriaRepository.findByClienteId(clienteId));
    }
}
