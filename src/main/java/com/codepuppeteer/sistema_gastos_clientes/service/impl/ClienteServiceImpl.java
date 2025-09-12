package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ClienteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponse createCliente(ClienteSave dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.usuarioId()));
        Cliente cliente = clienteMapper.toEntity(dto);
        cliente.setUsuario(usuario);
        Cliente saved = clienteRepository.save(cliente);
        return clienteMapper.toResponse(saved);
    }

    @Override
    public ClienteResponse updateCliente(Long id, ClienteUpdate dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        clienteMapper.updateFromDto(dto, cliente);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteResponse getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public List<ClienteList> getAllClientes() {
        return clienteMapper.toList(clienteRepository.findAll());
    }
}
