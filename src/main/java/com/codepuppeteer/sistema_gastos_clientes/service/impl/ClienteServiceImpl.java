package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.cliente.ClienteSave;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente crearCliente(ClienteSave clienteSave) {
        Usuario usuario = usuarioRepository.findById(clienteSave.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + clienteSave.usuarioId()));

        Cliente cliente = Cliente.builder()
                .usuario(usuario)
                .nombreCompleto(clienteSave.nombreCompleto())
                .telefono(clienteSave.telefono())
                .email(clienteSave.email())
                .fechaNacimiento(clienteSave.fechaNacimiento())
                .direccion(clienteSave.direccion())
                .documentoIdentidad(clienteSave.documentoIdentidad())
                .tipoDocumento(clienteSave.tipoDocumento() != null ? clienteSave.tipoDocumento() : TipoDocumento.DNI)
                .activo(true)
                .build();

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        Long usuarioId = cliente.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        cliente.setUsuario(usuario);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
