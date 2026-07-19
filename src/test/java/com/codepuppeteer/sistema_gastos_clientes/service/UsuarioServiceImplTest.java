package com.codepuppeteer.sistema_gastos_clientes.service;

import com.codepuppeteer.sistema_gastos_clientes.dto.usuario.UsuarioUpdate;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        when(usuarioRepository.findById(42L)).thenReturn(Optional.of(cliente));
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
}
