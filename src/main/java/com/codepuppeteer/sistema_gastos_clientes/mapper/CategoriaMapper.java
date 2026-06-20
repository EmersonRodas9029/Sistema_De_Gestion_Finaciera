package com.codepuppeteer.sistema_gastos_clientes.mapper;

import java.util.List;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.CategoriaList;
import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.CategoriaResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.CategoriaSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.CategoriaUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Categoria;

@Mapper(componentModel = "spring")
public abstract class CategoriaMapper {

    public abstract CategoriaResponse toResponse(Categoria categoria);

    public abstract CategoriaList toList(Categoria categoria);

    public abstract List<CategoriaList> toList(List<Categoria> categorias);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "activa", ignore = true)
    public abstract Categoria toEntity(CategoriaSave dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    public abstract void updateFromDto(CategoriaUpdate dto, @MappingTarget Categoria entity);

    @Named("toCategoria")
    protected Categoria toCategoria(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }

    @Named("toId")
    protected Long toId(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return Objects.requireNonNull(categoria.getId());
    }
}
