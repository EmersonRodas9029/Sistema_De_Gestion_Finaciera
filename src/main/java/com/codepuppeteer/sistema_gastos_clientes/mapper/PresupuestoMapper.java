package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.presupuesto.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Presupuesto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, CategoriaMapper.class})
public abstract class PresupuestoMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    public abstract PresupuestoResponse toResponse(Presupuesto presupuesto);

    public abstract PresupuestoList toList(Presupuesto presupuesto);

    public abstract List<PresupuestoList> toList(List<Presupuesto> presupuestos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "toCategoria")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract Presupuesto toEntity(PresupuestoSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(PresupuestoUpdate dto, @MappingTarget Presupuesto entity);
}
