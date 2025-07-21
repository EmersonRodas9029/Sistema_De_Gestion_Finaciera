CREATE TABLE cuentas_bancarias (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   cliente_id BIGINT NOT NULL,
                                   banco VARCHAR(255) NOT NULL,
                                   tipo_cuenta ENUM('AHORRO', 'CORRIENTE', 'OTRO') DEFAULT 'AHORRO',
                                   numero_cuenta VARCHAR(50) NOT NULL,
                                   saldo_actual DECIMAL(12,2) DEFAULT 0,
                                   moneda VARCHAR(10) DEFAULT 'USD',
                                   descripcion TEXT,
                                   activa BOOLEAN DEFAULT TRUE,
                                   fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                   UNIQUE KEY unique_cuenta_cliente (cliente_id, numero_cuenta),
                                   CONSTRAINT chk_saldo_no_negativo CHECK (saldo_actual >= 0)
);
