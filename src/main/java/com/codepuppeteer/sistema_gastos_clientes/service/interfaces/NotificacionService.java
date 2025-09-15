package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    Notificacion crearNotificacion(NotificacionSave dto);

    Notificacion actualizarNotificacion(Long id, NotificacionUpdate dto);

    void eliminarNotificacion(Long id);

    Optional<Notificacion> obtenerNotificacionPorId(Long id);

    List<Notificacion> obtenerTodasLasNotificaciones();
}
