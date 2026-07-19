package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.GastoSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.GastoUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Categoria;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.GastoMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.CategoriaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    private final GastoRepository gastoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoMapper gastoMapper;
    private final SecurityUtils securityUtils;

    @Override
    public Gasto crearGastoConRelaciones(GastoSave gastoSave) {
        Gasto gasto = gastoMapper.toEntity(gastoSave);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(gastoSave.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        gasto.setCliente(cliente);

        Long catId = gastoSave.categoriaId();
        if (catId != null) {
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            gasto.setCategoria(categoria);
        }

        return gastoRepository.save(gasto);
    }

    @Override
    public Gasto actualizarGastoConRelaciones(long id, GastoUpdate gastoUpdate) {
        Gasto existente = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        securityUtils.checkOwnership(existente.getCliente());

        gastoMapper.updateFromDto(gastoUpdate, existente);

        Long catId = gastoUpdate.categoriaId();
        if (catId != null) {
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            existente.setCategoria(null);
        }

        return gastoRepository.save(existente);
    }

    @Override
    public void eliminarGasto(long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        securityUtils.checkOwnership(gasto.getCliente());
        gastoRepository.delete(gasto);
    }

    @Override
    public Optional<Gasto> obtenerGastoPorId(long id) {
        Optional<Gasto> gasto = gastoRepository.findById(id);
        gasto.ifPresent(g -> securityUtils.checkOwnership(g.getCliente()));
        return gasto;
    }

    @Override
    public List<Gasto> obtenerTodosLosGastos() {
        if (!securityUtils.isContador()) {
            return gastoRepository.findByCliente_Usuario_Id(securityUtils.getCurrentUser().getUsuarioId());
        }
        return gastoRepository.findAll();
    }

    @Override
    public List<Gasto> obtenerGastosPorCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        return gastoRepository.findByClienteId(clienteId);
    }
}
