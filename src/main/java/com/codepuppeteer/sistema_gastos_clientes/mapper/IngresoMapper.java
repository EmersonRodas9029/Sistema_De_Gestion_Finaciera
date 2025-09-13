package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Ingreso;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngresoMapper {

    IngresoResponse toResponse(Ingreso ingreso);

    IngresoList toList(Ingreso ingreso);

    List<IngresoList> toList(List<Ingreso> ingresos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Ingreso toEntity(IngresoSave dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(IngresoUpdate dto, @MappingTarget Ingreso entity);
}
