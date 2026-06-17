package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public abstract class ClienteMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    public abstract ClienteResponse toResponse(Cliente cliente);

    public abstract ClienteList toList(Cliente cliente);

    public abstract List<ClienteList> toList(List<Cliente> clientes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "toUsuario")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    public abstract Cliente toEntity(ClienteSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(ClienteUpdate dto, @MappingTarget Cliente entity);

    @Named("toCliente")
    protected Cliente toCliente(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }

    @Named("toId")
    protected Long toId(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return cliente.getId();
    }
}
