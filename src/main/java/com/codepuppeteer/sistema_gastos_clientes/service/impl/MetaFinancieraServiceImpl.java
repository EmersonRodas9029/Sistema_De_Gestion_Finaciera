package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.MetaFinancieraRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.MetaFinancieraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MetaFinancieraServiceImpl implements MetaFinancieraService {

    private final MetaFinancieraRepository metaFinancieraRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public MetaFinanciera crearMeta(MetaFinanciera meta, long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        meta.setCliente(cliente);
        return metaFinancieraRepository.save(meta);
    }

    @Override
    public MetaFinanciera actualizarMeta(long id, MetaFinanciera meta) {
        MetaFinanciera existente = metaFinancieraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta financiera no encontrada"));

        existente.setNombre(meta.getNombre());
        existente.setDescripcion(meta.getDescripcion());
        existente.setMontoObjetivo(meta.getMontoObjetivo());
        existente.setMontoActual(meta.getMontoActual());
        existente.setFechaLimite(meta.getFechaLimite());
        existente.setPrioridad(meta.getPrioridad());
        existente.setActiva(meta.getActiva());
        existente.setCompletada(meta.getCompletada());
        existente.setFechaCompletada(meta.getFechaCompletada());

        return metaFinancieraRepository.save(existente);
    }

    @Override
    public void eliminarMeta(long id) {
        MetaFinanciera meta = metaFinancieraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta financiera no encontrada"));
        metaFinancieraRepository.delete(meta);
    }

    @Override
    public Optional<MetaFinanciera> obtenerMetaPorId(long id) {
        return metaFinancieraRepository.findById(id);
    }

    @Override
    public List<MetaFinanciera> obtenerTodasLasMetas() {
        return metaFinancieraRepository.findAll();
    }

    @Override
    public List<MetaFinanciera> obtenerMetasPorCliente(long clienteId) {
        return metaFinancieraRepository.findByClienteId(clienteId);
    }
}
