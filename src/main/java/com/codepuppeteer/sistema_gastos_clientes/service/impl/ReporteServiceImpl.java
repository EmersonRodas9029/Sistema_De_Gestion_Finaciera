package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ReporteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ReporteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository repository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReporteMapper mapper;

    @Override
    public Reporte crearReporte(ReporteSave dto) {
        Reporte reporte = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        reporte.setCliente(cliente);

        if (dto.contadorId() != null) {
            Usuario contador = usuarioRepository.findById(dto.contadorId())
                    .orElseThrow(() -> new RuntimeException("Contador no encontrado"));
            reporte.setContador(contador);
        }

        return repository.save(reporte);
    }

    @Override
    public Reporte actualizarReporte(Long id, ReporteUpdate dto) {
        Reporte existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        mapper.updateFromDto(dto, existente);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        existente.setCliente(cliente);

        if (dto.contadorId() != null) {
            Usuario contador = usuarioRepository.findById(dto.contadorId())
                    .orElseThrow(() -> new RuntimeException("Contador no encontrado"));
            existente.setContador(contador);
        } else {
            existente.setContador(null);
        }

        return repository.save(existente);
    }

    @Override
    public void eliminarReporte(Long id) {
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        repository.delete(reporte);
    }

    @Override
    public Optional<Reporte> obtenerReportePorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Reporte> obtenerTodosLosReportes() {
        return repository.findAll();
    }
}
