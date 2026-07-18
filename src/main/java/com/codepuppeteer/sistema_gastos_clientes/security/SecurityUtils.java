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

    public boolean isContador() {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CONTADOR"));
    }

    public void checkOwnership(Cliente cliente) {
        if (isContador()) return;
        if (cliente == null || cliente.getUsuario() == null
                || !cliente.getUsuario().getId().equals(getCurrentUser().getUsuarioId())) {
            throw new ForbiddenException("No tienes permiso para acceder a este recurso");
        }
    }
}
