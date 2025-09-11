CREATE TABLE metas_financieras (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   cliente_id BIGINT NOT NULL,
                                   nombre VARCHAR(255) NOT NULL,
                                   descripcion TEXT,
                                   monto_objetivo DECIMAL(10,2) NOT NULL,
                                   monto_actual DECIMAL(10,2) DEFAULT 0,
                                   fecha_limite DATE,
                                   prioridad ENUM('BAJA', 'MEDIA', 'ALTA') DEFAULT 'MEDIA',
                                   activa BOOLEAN DEFAULT TRUE,
                                   completada BOOLEAN DEFAULT FALSE,
                                   fecha_completada DATE NULL,
                                   fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                   CONSTRAINT chk_monto_objetivo_positivo CHECK (monto_objetivo > 0),
                                   CONSTRAINT chk_monto_actual_positivo CHECK (monto_actual >= 0),
                                   CONSTRAINT chk_monto_actual_menor_objetivo CHECK (monto_actual <= monto_objetivo)
);
