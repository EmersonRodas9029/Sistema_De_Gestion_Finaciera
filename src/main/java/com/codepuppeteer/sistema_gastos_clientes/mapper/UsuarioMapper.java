package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponse toResponse(Usuario usuario);

    UsuarioList toList(Usuario usuario);

    List<UsuarioList> toList(List<Usuario> usuarios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "intentosFallidos", ignore = true)
    @Mapping(target = "bloqueadoHasta", ignore = true)
    @Mapping(target = "tokenRecuperacion", ignore = true)
    @Mapping(target = "fechaExpiracionToken", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    Usuario toEntity(UsuarioSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "intentosFallidos", ignore = true)
    @Mapping(target = "bloqueadoHasta", ignore = true)
    @Mapping(target = "tokenRecuperacion", ignore = true)
    @Mapping(target = "fechaExpiracionToken", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    void updateFromDto(UsuarioUpdate dto, @MappingTarget Usuario entity);
}
