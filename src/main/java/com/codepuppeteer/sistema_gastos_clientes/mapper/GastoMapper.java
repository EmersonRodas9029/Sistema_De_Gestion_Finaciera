package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface GastoMapper {

    GastoMapper INSTANCE = Mappers.getMapper(GastoMapper.class);

    GastoResponse toResponse(Gasto gasto);

    GastoList toList(Gasto gasto);

    List<GastoList> toList(List<Gasto> gastos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Gasto toEntity(GastoSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(GastoUpdate dto, @MappingTarget Gasto entity);
}

