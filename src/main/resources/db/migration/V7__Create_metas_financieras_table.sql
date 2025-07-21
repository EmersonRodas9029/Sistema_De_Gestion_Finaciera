CREATE TABLE metas_financieras (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   cliente_id BIGINT NOT NULL,
                                   nombre VARCHAR(255) NOT NULL,
                                   descripcion TEXT,
                                   monto_objetivo DECIMAL(10,2) NOT NULL,
                                   monto_actual DECIMAL(10,2) DEFAULT 0,
                                   fecha_limite DATE,
                                   prioridad ENUM('ALTA', 'MEDIA', 'BAJA') DEFAULT 'MEDIA',
                                   cumplida BOOLEAN DEFAULT FALSE,
                                   activa BOOLEAN DEFAULT TRUE,
                                   fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                   CONSTRAINT chk_monto_objetivo_positivo CHECK (monto_objetivo > 0),
                                   CONSTRAINT chk_monto_actual_no_negativo CHECK (monto_actual >= 0)
);
