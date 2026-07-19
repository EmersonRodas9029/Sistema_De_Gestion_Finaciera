package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public abstract class MetaFinancieraMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    public abstract MetaFinancieraResponse toResponse(MetaFinanciera entity);

    @Mapping(source = "cliente.id", target = "clienteId")
    public abstract MetaFinancieraList toList(MetaFinanciera entity);

    public abstract List<MetaFinancieraList> toList(List<MetaFinanciera> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract MetaFinanciera toEntity(MetaFinancieraSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(MetaFinancieraUpdate dto, @MappingTarget MetaFinanciera entity);
}
