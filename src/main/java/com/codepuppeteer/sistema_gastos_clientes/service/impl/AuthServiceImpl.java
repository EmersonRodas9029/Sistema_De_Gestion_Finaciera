package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.auth.LoginRequest;
import com.codepuppeteer.sistema_gastos_clientes.dto.auth.LoginResponse;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.exception.UnauthorizedException;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.AuthService;
import com.codepuppeteer.sistema_gastos_clientes.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger SECURITY_LOG = LoggerFactory.getLogger("SECURITY");

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    SECURITY_LOG.warn("Login fallido: usuario inexistente '{}'", request.getUsername());
                    return new UnauthorizedException("Credenciales inválidas");
                });

        // Verificar si el usuario está bloqueado
        if (usuario.getBloqueadoHasta() != null && usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            SECURITY_LOG.warn("Login rechazado: usuario '{}' bloqueado hasta {}", usuario.getUsername(), usuario.getBloqueadoHasta());
            throw new UnauthorizedException("Usuario bloqueado temporalmente. Intente más tarde.");
        }

        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            SECURITY_LOG.warn("Login rechazado: usuario '{}' inactivo", usuario.getUsername());
            throw new UnauthorizedException("Usuario inactivo");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Resetear intentos fallidos en login exitoso
            if (usuario.getIntentosFallidos() > 0) {
                usuario.setIntentosFallidos(0);
                usuario.setBloqueadoHasta(null);
            }

            // Actualizar último acceso
            usuario.setUltimoAcceso(LocalDateTime.now());
            usuarioRepository.save(usuario);
            SECURITY_LOG.info("Login exitoso: usuario '{}' (id={}, rol={})", usuario.getUsername(), usuario.getId(), usuario.getRol());

            // Generar token JWT con información adicional
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", usuario.getId());
            claims.put("rol", usuario.getRol().name());
            claims.put("email", usuario.getEmail());

            String token = jwtUtil.generateToken(usuario.getUsername(), claims);

            return LoginResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .userId(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .rol(usuario.getRol().name())
                    .build();

        } catch (BadCredentialsException e) {
            handleFailedLogin(usuario);
            throw new UnauthorizedException("Credenciales inválidas");
        } catch (DisabledException e) {
            SECURITY_LOG.warn("Login rechazado: usuario '{}' deshabilitado", usuario.getUsername());
            throw new UnauthorizedException("Usuario deshabilitado");
        }
    }

    private void handleFailedLogin(Usuario usuario) {
        int intentos = usuario.getIntentosFallidos() + 1;
        usuario.setIntentosFallidos(intentos);

        // Bloquear usuario después de 5 intentos fallidos por 15 minutos
        if (intentos >= 5) {
            usuario.setBloqueadoHasta(LocalDateTime.now().plusMinutes(15));
            SECURITY_LOG.warn("Usuario '{}' bloqueado 15 minutos tras {} intentos fallidos", usuario.getUsername(), intentos);
        } else {
            SECURITY_LOG.warn("Login fallido: contraseña incorrecta para '{}' (intento {}/5)", usuario.getUsername(), intentos);
        }

        usuarioRepository.save(usuario);
    }
}
