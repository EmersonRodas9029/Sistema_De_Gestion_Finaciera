CREATE TABLE gastos_recurrentes (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    cliente_id BIGINT NOT NULL,
                                    categoria_id BIGINT,
                                    nombre VARCHAR(255) NOT NULL,
                                    monto DECIMAL(10,2) NOT NULL,
                                    frecuencia ENUM('DIARIO', 'SEMANAL', 'MENSUAL', 'ANUAL') NOT NULL,
                                    proxima_fecha DATE NOT NULL,
                                    metodo_pago ENUM('EFECTIVO', 'TARJETA_DEBITO', 'TARJETA_CREDITO', 'TRANSFERENCIA', 'CHEQUE', 'OTRO') DEFAULT 'TRANSFERENCIA',
                                    descripcion TEXT,
                                    activo BOOLEAN DEFAULT TRUE,
                                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                                    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL,
                                    CONSTRAINT chk_gasto_recurrente_monto CHECK (monto > 0)
);
