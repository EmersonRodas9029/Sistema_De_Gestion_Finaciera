package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;

import java.util.List;

public interface ClienteService {

    ClienteResponse createCliente(ClienteSave dto, Long usuarioId);

    ClienteResponse updateCliente(long id, ClienteUpdate dto);

    void deleteCliente(long id);

    ClienteResponse getClienteById(long id);

    List<ClienteList> getAllClientes();
}
