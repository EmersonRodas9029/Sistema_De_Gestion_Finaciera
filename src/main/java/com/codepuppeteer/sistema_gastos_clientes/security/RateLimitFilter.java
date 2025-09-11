package com.codepuppeteer.sistema_gastos_clientes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final ConcurrentHashMap<String, RequestInfo> requests = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 10;
    private final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requests.compute(ip, (k, v) -> {
            if (v == null || currentTime - v.startTime > TIME_WINDOW) {
                return new RequestInfo(1, currentTime);
            } else {
                v.count++;
                return v;
            }
        });

        if (requests.get(ip).count > MAX_REQUESTS) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Demasiadas solicitudes, espera un momento.");
            return;
        }

        filterChain.doFilter(request, response);
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
