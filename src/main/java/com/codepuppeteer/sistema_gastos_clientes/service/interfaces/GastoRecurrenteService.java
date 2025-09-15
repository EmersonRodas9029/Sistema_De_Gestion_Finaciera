package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;

import java.util.List;

public interface GastoRecurrenteService {
    GastoRecurrenteResponse save(GastoRecurrenteSave dto);
    GastoRecurrenteResponse update(Long id, GastoRecurrenteUpdate dto);
    void delete(Long id);
    GastoRecurrenteResponse getById(Long id);
    List<GastoRecurrenteList> getAllByCliente(Long clienteId);
}
