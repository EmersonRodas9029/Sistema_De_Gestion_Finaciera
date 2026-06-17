package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.*;

import java.util.List;

public interface CategoriaService {

    CategoriaResponse create(CategoriaSave dto);

    CategoriaResponse update(long id, CategoriaUpdate dto);

    void delete(long id);

    CategoriaResponse getById(long id);

    List<CategoriaList> getAllByCliente(long clienteId);
}
