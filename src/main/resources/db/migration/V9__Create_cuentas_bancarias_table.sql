CREATE TABLE cuentas_bancarias (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   cliente_id BIGINT NOT NULL,
                                   banco VARCHAR(255) NOT NULL,
                                   numero_cuenta VARCHAR(50) NOT NULL,
                                   tipo_cuenta ENUM('CORRIENTE', 'AHORRO', 'CREDITO') NOT NULL,
                                   saldo_actual DECIMAL(10,2) DEFAULT 0,
                                   fecha_actualizacion_saldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   activa BOOLEAN DEFAULT TRUE,
                                   fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                   UNIQUE KEY unique_cuenta (cliente_id, banco, numero_cuenta)
);
