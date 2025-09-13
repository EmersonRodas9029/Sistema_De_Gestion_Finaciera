package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Ingreso;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.IngresoMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.IngresoRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.IngresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngresoServiceImpl implements IngresoService {

    private final IngresoRepository ingresoRepository;
    private final ClienteRepository clienteRepository;
    private final IngresoMapper ingresoMapper;

    @Override
    public IngresoResponse crearIngreso(IngresoSave dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.clienteId()));

        Ingreso ingreso = ingresoMapper.toEntity(dto);
        ingreso.setCliente(cliente);

        return ingresoMapper.toResponse(ingresoRepository.save(ingreso));
    }

    @Override
    public IngresoResponse actualizarIngreso(Long id, IngresoUpdate dto) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado con id: " + id));

        ingresoMapper.updateFromDto(dto, ingreso);

        return ingresoMapper.toResponse(ingresoRepository.save(ingreso));
    }

    @Override
    public void eliminarIngreso(Long id) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado con id: " + id));

        ingresoRepository.delete(ingreso);
    }

    @Override
    public IngresoResponse obtenerIngresoPorId(Long id) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado con id: " + id));

        return ingresoMapper.toResponse(ingreso);
    }

    @Override
    public List<IngresoList> obtenerTodosLosIngresos() {
        return ingresoMapper.toList(ingresoRepository.findAll());
    }
}
