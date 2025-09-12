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
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    private final GastoRepository gastoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final GastoMapper gastoMapper;

    @Override
    public Gasto crearGastoConRelaciones(GastoSave gastoSave) {
        Gasto gasto = gastoMapper.toEntity(gastoSave);

        Cliente cliente = clienteRepository.findById(gastoSave.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        gasto.setCliente(cliente);

        if (gastoSave.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(gastoSave.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            gasto.setCategoria(categoria);
        }

        return gastoRepository.save(gasto);
    }

    @Override
    public Gasto actualizarGastoConRelaciones(Long id, GastoUpdate gastoUpdate) {
        Gasto existente = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));

        gastoMapper.updateFromDto(gastoUpdate, existente);

        if (gastoUpdate.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(gastoUpdate.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            existente.setCategoria(null);
        }

        return gastoRepository.save(existente);
    }

    @Override
    public void eliminarGasto(Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        gastoRepository.delete(gasto);
    }

    @Override
    public Optional<Gasto> obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id);
    }

    @Override
    public List<Gasto> obtenerTodosLosGastos() {
        return gastoRepository.findAll();
    }
}
