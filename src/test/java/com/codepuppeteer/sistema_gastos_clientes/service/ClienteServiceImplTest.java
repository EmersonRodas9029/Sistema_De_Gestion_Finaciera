package com.codepuppeteer.sistema_gastos_clientes.service;

import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import com.codepuppeteer.sistema_gastos_clientes.exception.ForbiddenException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ClienteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.security.SecurityUtils;
import com.codepuppeteer.sistema_gastos_clientes.security.UsuarioDetails;
import com.codepuppeteer.sistema_gastos_clientes.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Guarda de regresión para el hallazgo crítico: un cliente no debe poder leer el perfil
// (email, dirección, documento) de otro cliente por id. Usa un SecurityUtils real (no mockeado)
// para que checkOwnership() se ejecute de verdad contra un SecurityContextHolder de prueba.
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ClienteMapper clienteMapper;

    private ClienteServiceImpl service;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        SecurityUtils securityUtils = new SecurityUtils(null, null);
        service = new ClienteServiceImpl(clienteRepository, usuarioRepository, clienteMapper, securityUtils);

        Usuario dueño = new Usuario();
        dueño.setId(7L);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setUsuario(dueño);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
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
    void otroClienteNoPuedeLeerElPerfilDeAlguienMas() {
        loginComo(999L, Rol.CLIENTE);
        assertThrows(ForbiddenException.class, () -> service.getClienteById(1L));
    }

    @Test
    void elDueñoSiPuedeLeerSuPropioPerfil() {
        loginComo(7L, Rol.CLIENTE);
        assertDoesNotThrow(() -> service.getClienteById(1L));
        verify(clienteMapper).toResponse(cliente);
    }

    @Test
    void unContadorPuedeLeerCualquierPerfil() {
        loginComo(999L, Rol.CONTADOR);
        assertDoesNotThrow(() -> service.getClienteById(1L));
    }
}
