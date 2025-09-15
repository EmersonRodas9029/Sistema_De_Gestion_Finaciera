package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GastoRecurrenteMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "categoriaId", source = "categoria.id")
    GastoRecurrenteResponse toResponse(GastoRecurrente gasto);

    @Mapping(target = "id", source = "id")
    GastoRecurrenteList toList(GastoRecurrente gasto);

    List<GastoRecurrenteList> toList(List<GastoRecurrente> gastos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "ultimoProcesamiento", ignore = true)
    GastoRecurrente toEntity(GastoRecurrenteSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "ultimoProcesamiento", ignore = true)
    void updateFromDto(GastoRecurrenteUpdate dto, @MappingTarget GastoRecurrente entity);
}
