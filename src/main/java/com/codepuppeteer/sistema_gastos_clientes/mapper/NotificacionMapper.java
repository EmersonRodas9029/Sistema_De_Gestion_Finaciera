package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.notificacion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    NotificacionResponse toResponse(Notificacion notificacion);

    List<NotificacionResponse> toList(List<Notificacion> notificaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "leida", ignore = true)
    @Mapping(target = "fechaCreacion", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "fechaEnviada", ignore = true)
    Notificacion toEntity(NotificacionSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaEnviada", ignore = true)
    void updateFromDto(NotificacionUpdate dto, @MappingTarget Notificacion entity);
}
