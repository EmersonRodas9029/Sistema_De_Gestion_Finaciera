package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.cuenta.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.CuentaBancaria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaBancariaMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    CuentaBancariaResponse toResponse(CuentaBancaria cuenta);

    CuentaBancariaList toList(CuentaBancaria cuenta);

    List<CuentaBancariaList> toList(List<CuentaBancaria> cuentas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaActualizacionSaldo", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    CuentaBancaria toEntity(CuentaBancariaSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaActualizacionSaldo", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(CuentaBancariaUpdate dto, @MappingTarget CuentaBancaria entity);
}

