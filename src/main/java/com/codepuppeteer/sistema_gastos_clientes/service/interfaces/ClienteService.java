package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;

import java.util.List;

public interface ClienteService {

    ClienteResponse createCliente(ClienteSave dto);

    ClienteResponse updateCliente(Long id, ClienteUpdate dto);

    void deleteCliente(Long id);

    ClienteResponse getClienteById(Long id);

    List<ClienteList> getAllClientes();
}
