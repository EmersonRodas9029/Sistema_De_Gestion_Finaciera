package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.categoria.*;

import java.util.List;

public interface CategoriaService {

    CategoriaResponse create(CategoriaSave dto);

    CategoriaResponse update(Long id, CategoriaUpdate dto);

    void delete(Long id);

    CategoriaResponse getById(Long id);

    List<CategoriaList> getAllByCliente(Long clienteId);
}
