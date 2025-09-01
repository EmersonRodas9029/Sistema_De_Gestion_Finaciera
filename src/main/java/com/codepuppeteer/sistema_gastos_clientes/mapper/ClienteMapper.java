package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    List<ClienteList> toList(List<Cliente> clientes);

    @Mapping(target = "usuario", ignore = true) // Se setea en el service
    Cliente toEntity(ClienteSave clienteSave);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ClienteUpdate dto, @MappingTarget Cliente cliente);
}
