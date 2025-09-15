package com.codepuppeteer.sistema_gastos_clientes.dto.notificacion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoNotificacion;
import java.time.LocalDateTime;

public record NotificacionSave(
        Long clienteId,
        TipoNotificacion tipo,
        String titulo,
        String mensaje,
        Boolean leida,
        LocalDateTime fechaProgramada,
        LocalDateTime fechaEnviada,
        Boolean activa
) {}
