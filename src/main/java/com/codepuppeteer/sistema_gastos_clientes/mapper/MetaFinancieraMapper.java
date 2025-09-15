package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.meta_financiera.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetaFinancieraMapper {

    MetaFinancieraMapper INSTANCE = Mappers.getMapper(MetaFinancieraMapper.class);

    MetaFinancieraResponse toResponse(MetaFinanciera entity);

    MetaFinancieraList toList(MetaFinanciera entity);

    List<MetaFinancieraList> toList(List<MetaFinanciera> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    MetaFinanciera toEntity(MetaFinancieraSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(MetaFinancieraUpdate dto, @MappingTarget MetaFinanciera entity);
}
