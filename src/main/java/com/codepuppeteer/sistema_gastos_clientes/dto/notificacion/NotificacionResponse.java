package com.codepuppeteer.sistema_gastos_clientes.dto.notificacion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoNotificacion;
import java.time.LocalDateTime;

public record NotificacionResponse(
        Long id,
        Long clienteId,
        TipoNotificacion tipo,
        String titulo,
        String mensaje,
        Boolean leida,
        LocalDateTime fechaProgramada,
        LocalDateTime fechaEnviada,
        Boolean activa,
        LocalDateTime fechaCreacion
) {}
