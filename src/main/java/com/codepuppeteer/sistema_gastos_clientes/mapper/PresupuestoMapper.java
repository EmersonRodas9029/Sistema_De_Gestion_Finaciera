package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PresupuestoMapper {

    PresupuestoMapper INSTANCE = Mappers.getMapper(PresupuestoMapper.class);

    PresupuestoResponse toResponse(Presupuesto presupuesto);

    PresupuestoList toList(Presupuesto presupuesto);

    List<PresupuestoList> toList(List<Presupuesto> presupuestos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Presupuesto toEntity(PresupuestoSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(PresupuestoUpdate dto, @MappingTarget Presupuesto entity);
}
