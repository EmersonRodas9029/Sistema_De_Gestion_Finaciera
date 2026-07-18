package com.codepuppeteer.sistema_gastos_clientes.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final ConcurrentHashMap<String, RequestInfo> requests = new ConcurrentHashMap<>();

    @Value("${ratelimit.max-requests:200}")
    private int maxRequests;

    @Value("${ratelimit.window-ms:60000}")
    private long timeWindowMs;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requests.compute(ip, (k, v) -> {
            if (v == null || currentTime - v.startTime > timeWindowMs) {
                return new RequestInfo(1, currentTime);
            } else {
                v.count++;
                return v;
            }
        });

        if (requests.get(ip).count > maxRequests) {
            response.setStatus(429);
            response.getWriter().write("Demasiadas solicitudes, espera un momento.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    // ponytail: barrido simple por IPs inactivas; si el tráfico crece mucho, cambiar a un cache con expiración nativa (Caffeine).
    @Scheduled(fixedRate = 10 * 60 * 1000)
    void limpiarEntradasVencidas() {
        long ahora = System.currentTimeMillis();
        requests.entrySet().removeIf(e -> ahora - e.getValue().startTime > timeWindowMs);
    }

    private static class RequestInfo {
        int count;
        long startTime;

        RequestInfo(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}
