CREATE TABLE notificaciones (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                cliente_id BIGINT NOT NULL,
                                tipo ENUM('PRESUPUESTO_EXCEDIDO', 'GASTO_RECURRENTE', 'META_PROGRESO', 'RECORDATORIO', 'SISTEMA') NOT NULL,
                                titulo VARCHAR(255) NOT NULL,
                                mensaje TEXT NOT NULL,
                                leida BOOLEAN DEFAULT FALSE,
                                fecha_programada TIMESTAMP NULL,
                                fecha_enviada TIMESTAMP NULL,
                                activa BOOLEAN DEFAULT TRUE,
                                fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);
