package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, CategoriaMapper.class})
public abstract class GastoRecurrenteMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    public abstract GastoRecurrenteResponse toResponse(GastoRecurrente gasto);

    public abstract GastoRecurrenteList toList(GastoRecurrente gasto);

    public abstract List<GastoRecurrenteList> toList(List<GastoRecurrente> gastos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "toCategoria")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "ultimoProcesamiento", ignore = true)
    @Mapping(target = "activo", ignore = true)
    public abstract GastoRecurrente toEntity(GastoRecurrenteSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "ultimoProcesamiento", ignore = true)
    public abstract void updateFromDto(GastoRecurrenteUpdate dto, @MappingTarget GastoRecurrente entity);
}
