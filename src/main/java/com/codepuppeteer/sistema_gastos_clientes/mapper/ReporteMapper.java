package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, UsuarioMapper.class})
public abstract class ReporteMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "contador.id", target = "contadorId")
    public abstract ReporteResponse toResponse(Reporte reporte);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "contador.id", target = "contadorId")
    public abstract ReporteList toList(Reporte reporte);

    public abstract List<ReporteList> toList(List<Reporte> reportes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "toCliente")
    @Mapping(target = "contador", source = "contadorId", qualifiedByName = "toUsuario")
    @Mapping(target = "fechaGeneracion", ignore = true)
    public abstract Reporte toEntity(ReporteSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "contador", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    public abstract void updateFromDto(ReporteUpdate dto, @MappingTarget Reporte entity);
}
