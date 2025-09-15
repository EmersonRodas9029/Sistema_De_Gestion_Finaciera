package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    ReporteMapper INSTANCE = Mappers.getMapper(ReporteMapper.class);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "contadorId", source = "contador.id")
    ReporteResponse toResponse(Reporte reporte);

    ReporteList toList(Reporte reporte);

    List<ReporteList> toList(List<Reporte> reportes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "contador", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    Reporte toEntity(ReporteSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "contador", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    void updateFromDto(ReporteUpdate dto, @MappingTarget Reporte entity);
}
