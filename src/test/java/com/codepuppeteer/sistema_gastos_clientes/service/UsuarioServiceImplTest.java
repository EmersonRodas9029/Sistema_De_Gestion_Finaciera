package com.codepuppeteer.sistema_gastos_clientes.service;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioResponse;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioSave;
import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import com.codepuppeteer.sistema_gastos_clientes.exception.BusinessException;
import com.codepuppeteer.sistema_gastos_clientes.exception.ForbiddenException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ClienteMapper;
import com.codepuppeteer.sistema_gastos_clientes.mapper.UsuarioMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.*;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.security.UsuarioDetails;
import com.codepuppeteer.sistema_gastos_clientes.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Guarda de regresión para el hallazgo crítico: un CLIENTE no debe poder auto-promoverse
// a CONTADOR, reactivarse, ni cambiar su password vía PUT /api/usuarios/{id}. Usa un
// SecurityUtils real (no mockeado) para que las comprobaciones de rol se ejecuten de verdad.
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private ClienteMapper clienteMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private GastoRepository gastoRepository;
    @Mock private IngresoRepository ingresoRepository;
    @Mock private PresupuestoRepository presupuestoRepository;
    @Mock private MetaFinancieraRepository metaFinancieraRepository;
    @Mock private ReporteRepository reporteRepository;
    @Mock private CuentaBancariaRepository cuentaBancariaRepository;
    @Mock private NotificacionRepository notificacionRepository;
    @Mock private GastoRecurrenteRepository gastoRecurrenteRepository;
    @Mock private CategoriaRepository categoriaRepository;

    private UsuarioServiceImpl service;
    private Usuario cliente;

    @BeforeEach
    void setUp() {
        SecurityUtils securityUtils = new SecurityUtils(null, null);
        service = new UsuarioServiceImpl(usuarioRepository, clienteRepository, usuarioMapper, clienteMapper,
                passwordEncoder, gastoRepository, ingresoRepository, presupuestoRepository, metaFinancieraRepository,
                reporteRepository, cuentaBancariaRepository, notificacionRepository, gastoRecurrenteRepository,
                categoriaRepository, securityUtils);

        cliente = new Usuario();
        cliente.setId(42L);
        cliente.setRol(Rol.CLIENTE);
        cliente.setActivo(false);

        lenient().when(usuarioRepository.findById(42L)).thenReturn(Optional.of(cliente));
    }

    @AfterEach
    void limpiarContexto() {
        SecurityContextHolder.clearContext();
    }

    private void loginComo(long usuarioId, Rol rol) {
        Usuario u = new Usuario();
        u.setId(usuarioId);
        u.setRol(rol);
        UsuarioDetails details = new UsuarioDetails(u);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));
    }

    @Test
    void unClienteNoPuedeCambiarSuPropioRol() {
        loginComo(42L, Rol.CLIENTE);
        var dto = new UsuarioUpdate("juan", null, null, Rol.CONTADOR, null);
        assertThrows(ForbiddenException.class, () -> service.actualizarUsuario(42L, dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unClienteNoPuedeReactivarseASiMismo() {
        loginComo(42L, Rol.CLIENTE);
        var dto = new UsuarioUpdate("juan", null, null, null, true);
        assertThrows(ForbiddenException.class, () -> service.actualizarUsuario(42L, dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unClienteNoPuedeCambiarSuPasswordPorEsteEndpoint() {
        loginComo(42L, Rol.CLIENTE);
        var dto = new UsuarioUpdate("juan", "nuevaPassword123", null, null, null);
        assertThrows(ForbiddenException.class, () -> service.actualizarUsuario(42L, dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unClienteNoPuedeModificarAOtroUsuario() {
        loginComo(99L, Rol.CLIENTE);
        var dto = new UsuarioUpdate("juan", null, null, null, null);
        assertThrows(ForbiddenException.class, () -> service.actualizarUsuario(42L, dto));
    }

    @Test
    void unClienteNoPuedeVerOtroUsuarioPorId() {
        loginComo(99L, Rol.CLIENTE);
        assertThrows(ForbiddenException.class, () -> service.obtenerUsuarioPorId(42L));
    }

    @Test
    void unClienteNoPuedeCrearUsuarios() {
        loginComo(99L, Rol.CLIENTE);
        var dto = new UsuarioSave("nuevo", "password123", null, Rol.CLIENTE, null, null, null, null, null, null);
        assertThrows(ForbiddenException.class, () -> service.crearUsuario(dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unContadorNoPuedeSuperarElMaximoDeUsuariosCreados() {
        loginComo(7L, Rol.CONTADOR);
        Usuario contadorUser = new Usuario();
        contadorUser.setId(7L);
        contadorUser.setRol(Rol.CONTADOR);
        contadorUser.setLimiteUsuarios(5);
        when(usuarioRepository.findById(7L)).thenReturn(Optional.of(contadorUser));
        when(usuarioRepository.countByContadorId(7L)).thenReturn(5L);
        var dto = new UsuarioSave("nuevo", "password123", null, Rol.CLIENTE, null, null, null, null, null, null);
        assertThrows(BusinessException.class, () -> service.crearUsuario(dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unContadorNoPuedeCrearUnUsuarioSudo() {
        loginComo(7L, Rol.CONTADOR);
        var dto = new UsuarioSave("nuevo", "password123", null, Rol.SUDO, null, null, null, null, null, null);
        assertThrows(ForbiddenException.class, () -> service.crearUsuario(dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unContadorNoPuedeCrearOtroContador() {
        loginComo(7L, Rol.CONTADOR);
        var dto = new UsuarioSave("nuevo", "password123", null, Rol.CONTADOR, null, null, null, null, null, null);
        assertThrows(ForbiddenException.class, () -> service.crearUsuario(dto));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unSudoNoEstaLimitadoPorElMaximoDeUsuarios() {
        loginComo(1L, Rol.SUDO);
        Usuario sudoUser = new Usuario();
        sudoUser.setId(1L);
        sudoUser.setRol(Rol.SUDO);
        lenient().when(usuarioRepository.findById(1L)).thenReturn(Optional.of(sudoUser));
        var dto = new UsuarioSave("nuevo", "password123", null, Rol.CLIENTE, null, null, null, null, null, null);
        var entidad = new Usuario();
        entidad.setId(200L);
        entidad.setRol(Rol.CLIENTE);
        when(usuarioMapper.toEntity(dto)).thenReturn(entidad);
        when(usuarioRepository.save(any())).thenReturn(entidad);
        when(usuarioMapper.toResponse(entidad)).thenReturn(
                new UsuarioResponse(200L, "nuevo", null, Rol.CLIENTE, true, null, null, null, null, null, null));

        service.crearUsuario(dto);

        verify(usuarioRepository).save(any());
    }

    @Test
    void unContadorNoPuedeCambiarSuPropioLimiteDeUsuarios() {
        loginComo(7L, Rol.CONTADOR);
        assertThrows(ForbiddenException.class, () -> service.actualizarLimiteUsuarios(7L, 10));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unSudoPuedeCambiarElLimiteDeUsuariosDeUnContador() {
        loginComo(1L, Rol.SUDO);
        Usuario contadorUser = new Usuario();
        contadorUser.setId(7L);
        contadorUser.setRol(Rol.CONTADOR);
        contadorUser.setLimiteUsuarios(5);
        when(usuarioRepository.findById(7L)).thenReturn(Optional.of(contadorUser));

        service.actualizarLimiteUsuarios(7L, 10);

        assertEquals(10, contadorUser.getLimiteUsuarios());
        verify(usuarioRepository).save(contadorUser);
    }

    @Test
    void unSudoNoPuedeCambiarElLimiteDeUnUsuarioQueNoEsContador() {
        loginComo(1L, Rol.SUDO);
        when(usuarioRepository.findById(42L)).thenReturn(Optional.of(cliente));
        assertThrows(BusinessException.class, () -> service.actualizarLimiteUsuarios(42L, 10));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unContadorSoloVeSuPropiaCuentaYSusUsuariosAfiliados() {
        loginComo(7L, Rol.CONTADOR);
        Usuario yoMismo = new Usuario();
        yoMismo.setId(7L);
        yoMismo.setRol(Rol.CONTADOR);
        Usuario miAfiliado = new Usuario();
        miAfiliado.setId(100L);
        miAfiliado.setRol(Rol.CLIENTE);
        miAfiliado.setContador(yoMismo);
        when(usuarioRepository.findByIdOrContadorId(7L, 7L)).thenReturn(java.util.List.of(yoMismo, miAfiliado));
        lenient().when(usuarioMapper.toResponse(yoMismo)).thenReturn(
                new UsuarioResponse(7L, "yo", null, Rol.CONTADOR, true, null, null, null, null, null, 5));
        lenient().when(usuarioMapper.toResponse(miAfiliado)).thenReturn(
                new UsuarioResponse(100L, "afiliado", null, Rol.CLIENTE, true, null, null, null, null, 7L, null));

        var resultado = service.obtenerTodosLosUsuarios();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, never()).findAll(any(org.springframework.data.domain.PageRequest.class));
    }

    @Test
    void unContadorNoPuedeVerElUsuarioDeOtroContador() {
        loginComo(7L, Rol.CONTADOR);
        Usuario otroContador = new Usuario();
        otroContador.setId(8L);
        otroContador.setRol(Rol.CONTADOR);
        Usuario usuarioAjeno = new Usuario();
        usuarioAjeno.setId(55L);
        usuarioAjeno.setRol(Rol.CLIENTE);
        usuarioAjeno.setContador(otroContador);
        when(usuarioRepository.findById(55L)).thenReturn(Optional.of(usuarioAjeno));

        assertThrows(ForbiddenException.class, () -> service.obtenerUsuarioPorId(55L));
    }
}
