package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.PresupuestoUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.PresupuestoMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.CategoriaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.PresupuestoRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.PresupuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PresupuestoServiceImpl implements PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final PresupuestoMapper mapper;

    @Override
    public Presupuesto crearPresupuestoConRelaciones(PresupuestoSave dto) {
        Presupuesto presupuesto = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        presupuesto.setCliente(cliente);

        if (dto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            presupuesto.setCategoria(categoria);
        }

        return presupuestoRepository.save(presupuesto);
    }

    @Override
    public Presupuesto actualizarPresupuestoConRelaciones(Long id, PresupuestoUpdate dto) {
        Presupuesto existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));

        mapper.updateFromDto(dto, existente);

        if (dto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            existente.setCategoria(null);
        }

        return presupuestoRepository.save(existente);
    }

    @Override
    public void eliminarPresupuesto(Long id) {
        Presupuesto existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        presupuestoRepository.delete(existente);
    }

    @Override
    public Optional<Presupuesto> obtenerPresupuestoPorId(Long id) {
        return presupuestoRepository.findById(id);
    }

    @Override
    public List<Presupuesto> obtenerTodosLosPresupuestos() {
        return presupuestoRepository.findAll();
    }

    @Override
    public List<Presupuesto> obtenerPresupuestosPorCliente(Long clienteId) {
        return presupuestoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Presupuesto> obtenerPresupuestosPorCategoria(Long categoriaId) {
        return presupuestoRepository.findByCategoriaId(categoriaId);
    }
}
