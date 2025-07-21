CREATE TABLE notificaciones (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                cliente_id BIGINT NOT NULL,
                                tipo ENUM('AVISO', 'RECORDATORIO', 'ALERTA', 'OTRO') DEFAULT 'AVISO',
                                mensaje TEXT NOT NULL,
                                leido BOOLEAN DEFAULT FALSE,
                                fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                prioridad ENUM('ALTA', 'MEDIA', 'BAJA') DEFAULT 'MEDIA',
                                FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);
