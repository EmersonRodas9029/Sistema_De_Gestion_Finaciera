package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.UsuarioMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse crearUsuario(UsuarioSave usuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        Usuario saved = usuarioRepository.save(usuario);
        if (saved.getRol() == Rol.CLIENTE) {
            clienteRepository.save(Cliente.builder()
                    .usuario(saved)
                    .nombreCompleto(usuarioDto.nombreCompleto() != null ? usuarioDto.nombreCompleto() : saved.getUsername())
                    .email(saved.getEmail())
                    .telefono(usuarioDto.telefono())
                    .fechaNacimiento(usuarioDto.fechaNacimiento())
                    .documentoIdentidad(usuarioDto.documentoIdentidad())
                    .tipoDocumento(usuarioDto.tipoDocumento())
                    .direccion(usuarioDto.direccion())
                    .build());
        }
        return usuarioMapper.toResponse(saved);
    }

    @Override
    public UsuarioResponse actualizarUsuario(long id, UsuarioUpdate usuarioDto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuarioMapper.updateFromDto(usuarioDto, existente);
        return usuarioMapper.toResponse(usuarioRepository.save(existente));
    }

    @Override
    public void eliminarUsuario(long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        existente.setActivo(false);
        usuarioRepository.save(existente);
        clienteRepository.findByUsuarioId(id).ifPresent(c -> {
            c.setActivo(false);
            clienteRepository.save(c);
        });
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    public void bloquearUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void desbloquearUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
}
