package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.ingreso.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Ingreso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface IngresoMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    IngresoResponse toResponse(Ingreso ingreso);

    @Mapping(source = "cliente.id", target = "clienteId")
    IngresoList toList(Ingreso ingreso);

    List<IngresoList> toList(List<Ingreso> ingresos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Ingreso toEntity(IngresoSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(IngresoUpdate dto, @MappingTarget Ingreso entity);
}
