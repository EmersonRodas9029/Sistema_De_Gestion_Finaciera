package com.codepuppeteer.sistema_gastos_clientes.dto.notificacion;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record NotificacionSave(
        @NotNull(message = "El ID del cliente es obligatorio")
        Long clienteId,

        @NotNull(message = "El tipo de notificación es obligatorio")
        TipoNotificacion tipo,

        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        Boolean leida,
        LocalDateTime fechaProgramada,
        LocalDateTime fechaEnviada,
        Boolean activa
) {}
