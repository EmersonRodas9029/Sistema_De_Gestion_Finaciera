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
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.PresupuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PresupuestoServiceImpl implements PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final PresupuestoMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public Presupuesto crearPresupuestoConRelaciones(PresupuestoSave dto) {
        Presupuesto presupuesto = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        presupuesto.setCliente(cliente);

        Long catId = dto.categoriaId();
        if (catId != null) {
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            presupuesto.setCategoria(categoria);
        }

        return presupuestoRepository.save(presupuesto);
    }

    @Override
    public Presupuesto actualizarPresupuestoConRelaciones(long id, PresupuestoUpdate dto) {
        Presupuesto existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        securityUtils.checkOwnership(existente.getCliente());

        mapper.updateFromDto(dto, existente);

        Long catId = dto.categoriaId();
        if (catId != null) {
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            existente.setCategoria(null);
        }

        return presupuestoRepository.save(existente);
    }

    @Override
    public void eliminarPresupuesto(long id) {
        Presupuesto existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));
        securityUtils.checkOwnership(existente.getCliente());
        presupuestoRepository.delete(existente);
    }

    @Override
    public Optional<Presupuesto> obtenerPresupuestoPorId(long id) {
        Optional<Presupuesto> presupuesto = presupuestoRepository.findById(id);
        presupuesto.ifPresent(p -> securityUtils.checkOwnership(p.getCliente()));
        return presupuesto;
    }

    @Override
    public List<Presupuesto> obtenerTodosLosPresupuestos() {
        if (!securityUtils.isContador()) {
            return presupuestoRepository.findByCliente_Usuario_Id(securityUtils.getCurrentUser().getUsuarioId());
        }
        return presupuestoRepository.findAll();
    }

    @Override
    public List<Presupuesto> obtenerPresupuestosPorCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        return presupuestoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Presupuesto> obtenerPresupuestosPorCategoria(long categoriaId) {
        if (!securityUtils.isContador()) {
            return presupuestoRepository.findByCategoriaIdAndCliente_Usuario_Id(
                    categoriaId, securityUtils.getCurrentUser().getUsuarioId());
        }
        return presupuestoRepository.findByCategoriaId(categoriaId);
    }
}
