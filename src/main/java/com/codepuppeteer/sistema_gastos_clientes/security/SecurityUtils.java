package com.codepuppeteer.sistema_gastos_clientes.security;

import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.exception.ForbiddenException;
import com.codepuppeteer.sistema_gastos_clientes.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtUtil jwtUtil;

    public String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    public UsuarioDetails getUserDetails(String username) {
        return (UsuarioDetails) usuarioDetailsService.loadUserByUsername(username);
    }

    public HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public UsuarioDetails getCurrentUser() {
        return (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // SUDO hereda todos los permisos de CONTADOR: en vez de repetir "|| isSudo()" en cada
    // chequeo de autorización del sistema, isContador() ya cubre ambos roles en un solo lugar.
    public boolean isContador() {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CONTADOR")
                        || authority.getAuthority().equals("ROLE_SUDO"));
    }

    public boolean isSudo() {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_SUDO"));
    }

    public void checkOwnership(Cliente cliente) {
        if (isContador()) return;
        if (cliente == null || cliente.getUsuario() == null
                || !cliente.getUsuario().getId().equals(getCurrentUser().getUsuarioId())) {
            throw new ForbiddenException("No tienes permiso para acceder a este recurso");
        }
    }
}
