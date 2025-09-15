package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfiguracionMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    ConfiguracionResponse toResponse(Configuracion configuracion);

    @Mapping(target = "clienteId", source = "cliente.id")
    ConfiguracionList toList(Configuracion configuracion);

    List<ConfiguracionList> toList(List<Configuracion> configuraciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Configuracion toEntity(ConfiguracionSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(ConfiguracionUpdate dto, @MappingTarget Configuracion entity);
}
