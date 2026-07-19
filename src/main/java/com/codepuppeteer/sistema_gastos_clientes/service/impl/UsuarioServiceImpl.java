package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.CambiarPasswordRequest;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import com.codepuppeteer.sistema_gastos_clientes.exception.BusinessException;
import com.codepuppeteer.sistema_gastos_clientes.exception.ForbiddenException;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.exception.UnauthorizedException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ClienteMapper;
import com.codepuppeteer.sistema_gastos_clientes.mapper.UsuarioMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.CategoriaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.CuentaBancariaRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRecurrenteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.IngresoRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.MetaFinancieraRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.NotificacionRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.PresupuestoRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ReporteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger SECURITY_LOG = LoggerFactory.getLogger("SECURITY");

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioMapper usuarioMapper;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;
    private final GastoRepository gastoRepository;
    private final IngresoRepository ingresoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final MetaFinancieraRepository metaFinancieraRepository;
    private final ReporteRepository reporteRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final NotificacionRepository notificacionRepository;
    private final GastoRecurrenteRepository gastoRecurrenteRepository;
    private final CategoriaRepository categoriaRepository;
    private final SecurityUtils securityUtils;

    // Usuario no tiene referencia directa a Cliente (la FK vive del lado de Cliente),
    // así que el cliente asociado se resuelve aparte y se agrega al DTO de respuesta.
    private UsuarioResponse toResponseConCliente(Usuario usuario) {
        UsuarioResponse base = usuarioMapper.toResponse(usuario);
        var cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .map(clienteMapper::toResponse)
                .orElse(null);
        return new UsuarioResponse(base.id(), base.username(), base.email(), base.rol(), base.activo(),
                base.ultimoAcceso(), base.fechaCreacion(), base.fechaModificacion(), cliente);
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioSave usuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setPassword(passwordEncoder.encode(usuarioDto.password()));
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
        return toResponseConCliente(saved);
    }

    @Override
    public UsuarioResponse actualizarUsuario(long id, UsuarioUpdate usuarioDto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        boolean esContador = securityUtils.isContador();
        boolean esPropio = securityUtils.getCurrentUser().getUsuarioId().equals(id);
        if (!esContador && !esPropio) {
            throw new ForbiddenException("No tienes permiso para modificar este usuario");
        }
        if (!esContador) {
            if (usuarioDto.rol() != null && usuarioDto.rol() != existente.getRol()) {
                throw new ForbiddenException("No tienes permiso para cambiar tu rol");
            }
            if (usuarioDto.activo() != null && !usuarioDto.activo().equals(existente.getActivo())) {
                throw new ForbiddenException("No tienes permiso para cambiar el estado de la cuenta");
            }
            if (usuarioDto.password() != null && !usuarioDto.password().isBlank()) {
                throw new ForbiddenException("Usa el endpoint de cambio de contraseña para actualizar tu contraseña");
            }
        }

        if (esContador && usuarioDto.rol() != null && usuarioDto.rol() != existente.getRol()) {
            SECURITY_LOG.warn("Cambio de rol: usuario '{}' cambia el rol de '{}' (id={}) de {} a {}",
                    securityUtils.getCurrentUser().getUsername(), existente.getUsername(), id,
                    existente.getRol(), usuarioDto.rol());
        }
        if (esContador && usuarioDto.activo() != null && !usuarioDto.activo().equals(existente.getActivo())) {
            SECURITY_LOG.warn("Cambio de estado: usuario '{}' pone activo={} en '{}' (id={})",
                    securityUtils.getCurrentUser().getUsername(), usuarioDto.activo(), existente.getUsername(), id);
        }

        usuarioMapper.updateFromDto(usuarioDto, existente);
        if (esContador && usuarioDto.password() != null && !usuarioDto.password().isBlank()) {
            existente.setPassword(passwordEncoder.encode(usuarioDto.password()));
        }
        return toResponseConCliente(usuarioRepository.save(existente));
    }

    @Override
    public void cambiarPassword(long id, CambiarPasswordRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        if (!securityUtils.isContador() && !securityUtils.getCurrentUser().getUsuarioId().equals(id)) {
            throw new ForbiddenException("No tienes permiso para cambiar la contraseña de otro usuario");
        }

        if (!passwordEncoder.matches(request.passwordActual(), usuario.getPassword())) {
            throw new UnauthorizedException("La contraseña actual no coincide");
        }

        usuario.setPassword(passwordEncoder.encode(request.passwordNueva()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        if (!securityUtils.isContador() && !securityUtils.getCurrentUser().getUsuarioId().equals(id)) {
            throw new ForbiddenException("No tienes permiso para desactivar este usuario");
        }
        existente.setActivo(false);
        usuarioRepository.save(existente);
        clienteRepository.findByUsuarioId(id).ifPresent(c -> {
            c.setActivo(false);
            clienteRepository.save(c);
        });
    }

    @Override
    public void activarUsuario(long id) {
        if (!securityUtils.isContador()) {
            throw new ForbiddenException("No tienes permiso para reactivar usuarios");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setActivo(true);
        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);
        usuarioRepository.save(usuario);
        clienteRepository.findByUsuarioId(id).ifPresent(c -> {
            c.setActivo(true);
            clienteRepository.save(c);
        });
    }

    @Override
    public void eliminarUsuarioPermanente(long id) {
        if (!securityUtils.isContador()) {
            throw new ForbiddenException("No tienes permiso para eliminar usuarios permanentemente");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        clienteRepository.findByUsuarioId(id).ifPresent(cliente -> {
            long clienteId = cliente.getId();
            boolean tieneRegistrosAsociados = gastoRepository.existsByClienteId(clienteId)
                    || ingresoRepository.existsByClienteId(clienteId)
                    || presupuestoRepository.existsByClienteId(clienteId)
                    || metaFinancieraRepository.existsByClienteId(clienteId)
                    || reporteRepository.existsByClienteId(clienteId)
                    || cuentaBancariaRepository.existsByClienteId(clienteId)
                    || notificacionRepository.existsByClienteId(clienteId)
                    || gastoRecurrenteRepository.existsByClienteId(clienteId)
                    || categoriaRepository.existsByClienteId(clienteId);
            if (tieneRegistrosAsociados) {
                throw new BusinessException(
                        "No se puede eliminar definitivamente: el cliente tiene registros asociados " +
                        "(gastos, ingresos, presupuestos, metas, etc.). Debe permanecer desactivado para conservar su historial."
                );
            }
            clienteRepository.delete(cliente);
        });

        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        if (!securityUtils.isContador() && !securityUtils.getCurrentUser().getUsuarioId().equals(id)) {
            throw new ForbiddenException("No tienes permiso para ver este usuario");
        }
        return toResponseConCliente(usuario);
    }

    @Override
    public List<UsuarioResponse> obtenerTodosLosUsuarios() {
        if (!securityUtils.isContador()) {
            return usuarioRepository.findById(securityUtils.getCurrentUser().getUsuarioId())
                    .map(u -> List.of(toResponseConCliente(u)))
                    .orElse(List.of());
        }
        // ponytail: tope duro de 1000 en vez de findAll() sin límite; si el volumen de usuarios
        // supera eso en la práctica, aquí es donde se añade paginación real de cara al frontend.
        return usuarioRepository.findAll(PageRequest.of(0, 1000))
                .map(this::toResponseConCliente)
                .toList();
    }

    @Override
    public void bloquearUsuario(long id) {
        if (!securityUtils.isContador()) {
            throw new ForbiddenException("No tienes permiso para bloquear usuarios");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void desbloquearUsuario(long id) {
        if (!securityUtils.isContador()) {
            throw new ForbiddenException("No tienes permiso para desbloquear usuarios");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
}
