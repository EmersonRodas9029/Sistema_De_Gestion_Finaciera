package com.codepuppeteer.sistema_gastos_clientes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class StartupLogger {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${server.port:8080}")
    private String port;

    @EventListener(ApplicationReadyEvent.class)
    void onReady() {
        System.out.println();
        System.out.println("  Estado del sistema");
        System.out.println("  ------------------");
        System.out.println("  Base de datos : " + dbUrl);
        System.out.println("  API           : http://localhost:" + port + "/api");
        System.out.println("  Migraciones   : OK");
        System.out.println("  Servidor      : CORRIENDO");
        System.out.println();
    }
}
