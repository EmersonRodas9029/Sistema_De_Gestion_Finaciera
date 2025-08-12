CREATE TABLE reportes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          cliente_id BIGINT NOT NULL,
                          contador_id BIGINT NULL,
                          nombre VARCHAR(255) NOT NULL,
                          tipo_reporte ENUM('GASTOS_MENSUAL', 'GASTOS_ANUAL', 'INGRESOS_MENSUAL', 'INGRESOS_ANUAL', 'PRESUPUESTO', 'PERSONALIZADO') NOT NULL,
                          periodo_inicio DATE NOT NULL,
                          periodo_fin DATE NOT NULL,
                          contenido JSON,
                          ruta_archivo VARCHAR(500),
                          fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                          FOREIGN KEY (contador_id) REFERENCES usuarios(id) ON DELETE SET NULL,
                          CONSTRAINT chk_periodo_valido CHECK (periodo_fin >= periodo_inicio)
);
