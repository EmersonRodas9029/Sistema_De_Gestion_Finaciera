package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.NotificacionMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.NotificacionRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;
    private final ClienteRepository clienteRepository;
    private final NotificacionMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public Notificacion crearNotificacion(NotificacionSave dto) {
        Notificacion notificacion = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        notificacion.setCliente(cliente);

        if (notificacion.getActiva() == null) notificacion.setActiva(true);
        if (notificacion.getLeida() == null) notificacion.setLeida(false);

        return repository.save(notificacion);
    }

    @Override
    public Notificacion actualizarNotificacion(long id, NotificacionUpdate dto) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        securityUtils.checkOwnership(notificacion.getCliente());

        mapper.updateFromDto(dto, notificacion);
        return repository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(long id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        securityUtils.checkOwnership(notificacion.getCliente());
        repository.delete(notificacion);
    }

    @Override
    public Optional<Notificacion> obtenerNotificacionPorId(long id) {
        Optional<Notificacion> notificacion = repository.findById(id);
        notificacion.ifPresent(n -> securityUtils.checkOwnership(n.getCliente()));
        return notificacion;
    }

    @Override
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = repository.findAll();
        if (!securityUtils.isContador()) {
            Long usuarioId = securityUtils.getCurrentUser().getUsuarioId();
            return notificaciones.stream()
                    .filter(n -> n.getCliente() != null && n.getCliente().getUsuario().getId().equals(usuarioId))
                    .toList();
        }
        return notificaciones;
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPorCliente(long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        securityUtils.checkOwnership(cliente);
        return repository.findByClienteId(clienteId);
    }
}
