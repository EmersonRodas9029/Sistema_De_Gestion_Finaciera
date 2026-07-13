package com.codepuppeteer.sistema_gastos_clientes.mapper;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    // Mapeo de entity a DTO de respuesta
    public abstract UsuarioResponse toResponse(Usuario usuario);

    // Mapeo de entity a DTO de lista
    public abstract UsuarioList toList(Usuario usuario);

    public abstract List<UsuarioList> toList(List<Usuario> usuarios);

    // Mapeo de DTO de creación a entity
    // password se ignora aquí: UsuarioServiceImpl lo encodea con PasswordEncoder antes de guardar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "intentosFallidos", ignore = true)
    @Mapping(target = "bloqueadoHasta", ignore = true)
    @Mapping(target = "tokenRecuperacion", ignore = true)
    @Mapping(target = "fechaExpiracionToken", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract Usuario toEntity(UsuarioSave dto);

    // Mapeo de DTO de actualización a entity
    // password se ignora aquí: UsuarioServiceImpl lo encodea con PasswordEncoder solo si viene informado
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "intentosFallidos", ignore = true)
    @Mapping(target = "bloqueadoHasta", ignore = true)
    @Mapping(target = "tokenRecuperacion", ignore = true)
    @Mapping(target = "fechaExpiracionToken", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(UsuarioUpdate dto, @MappingTarget Usuario entity);

    @Named("toUsuario")
    protected Usuario toUsuario(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Named("toId")
    protected Long toId(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return Objects.requireNonNull(usuario.getId());
    }
}
