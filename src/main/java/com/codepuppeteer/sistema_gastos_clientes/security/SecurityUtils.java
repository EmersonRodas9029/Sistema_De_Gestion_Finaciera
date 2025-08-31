package com.codepuppeteer.sistema_gastos_clientes.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UsuarioDetailsService usuarioDetailsService;

    public String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return token != null && !token.isEmpty();
    }

    public String getUsernameFromToken(String token) {
        return token;
    }

    public UsuarioDetails getUserDetails(String username) {
        return (UsuarioDetails) usuarioDetailsService.loadUserByUsername(username);
    }

    public HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
