package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.gastorecurrente.*;

import java.util.List;

public interface GastoRecurrenteService {
    GastoRecurrenteResponse save(GastoRecurrenteSave dto);
    GastoRecurrenteResponse update(long id, GastoRecurrenteUpdate dto);
    void delete(long id);
    GastoRecurrenteResponse getById(long id);
    List<GastoRecurrenteList> getAllByCliente(long clienteId);
}
