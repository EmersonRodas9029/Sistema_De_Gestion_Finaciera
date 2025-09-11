CREATE TABLE configuraciones (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 cliente_id BIGINT NOT NULL,
                                 clave VARCHAR(100) NOT NULL,
                                 valor TEXT NOT NULL,
                                 tipo ENUM('STRING', 'NUMBER', 'BOOLEAN', 'JSON') DEFAULT 'STRING',
                                 descripcion TEXT,
                                 fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                 UNIQUE KEY unique_config_cliente (cliente_id, clave)
);
