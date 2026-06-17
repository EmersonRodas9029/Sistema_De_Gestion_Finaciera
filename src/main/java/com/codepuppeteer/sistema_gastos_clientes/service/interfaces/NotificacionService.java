package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    Notificacion crearNotificacion(NotificacionSave dto);

    Notificacion actualizarNotificacion(long id, NotificacionUpdate dto);

    void eliminarNotificacion(long id);

    Optional<Notificacion> obtenerNotificacionPorId(long id);

    List<Notificacion> obtenerTodasLasNotificaciones();
}
