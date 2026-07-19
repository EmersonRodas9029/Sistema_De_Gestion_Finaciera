package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.gasto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, CategoriaMapper.class})
public abstract class GastoMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    public abstract GastoResponse toResponse(Gasto gasto);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    public abstract GastoList toList(Gasto gasto);

    public abstract List<GastoList> toList(List<Gasto> gastos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "toCategoria")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract Gasto toEntity(GastoSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(GastoUpdate dto, @MappingTarget Gasto entity);
}

