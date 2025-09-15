package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import com.codepuppeteer.sistema_gastos_clientes.mapper.NotificacionMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.NotificacionRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;
    private final ClienteRepository clienteRepository;
    private final NotificacionMapper mapper;

    @Override
    public Notificacion crearNotificacion(NotificacionSave dto) {
        Notificacion notificacion = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        notificacion.setCliente(cliente);

        if (notificacion.getActiva() == null) notificacion.setActiva(true);
        if (notificacion.getLeida() == null) notificacion.setLeida(false);

        return repository.save(notificacion);
    }

    @Override
    public Notificacion actualizarNotificacion(Long id, NotificacionUpdate dto) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        mapper.updateFromDto(dto, notificacion);
        return repository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        repository.delete(notificacion);
    }

    @Override
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return repository.findAll();
    }
}
