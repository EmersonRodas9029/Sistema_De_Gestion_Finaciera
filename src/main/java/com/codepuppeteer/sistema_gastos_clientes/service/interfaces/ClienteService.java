package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteSave;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> obtenerTodosLosClientes();

    Optional<Cliente> obtenerClientePorId(Long id);

    Cliente crearCliente(ClienteSave clienteSave);

    Cliente actualizarCliente(Long id, Cliente cliente);

    void eliminarCliente(Long id);
}
