CREATE TABLE gastos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        cliente_id BIGINT NOT NULL,
                        categoria_id BIGINT,
                        monto DECIMAL(10,2) NOT NULL,
                        fecha DATE NOT NULL,
                        descripcion TEXT,
                        metodo_pago ENUM('EFECTIVO', 'TARJETA_DEBITO', 'TARJETA_CREDITO', 'TRANSFERENCIA', 'CHEQUE', 'OTRO') DEFAULT 'EFECTIVO',
                        es_recurrente BOOLEAN DEFAULT FALSE,
                        frecuencia ENUM('DIARIO', 'SEMANAL', 'MENSUAL', 'ANUAL') NULL,
                        adjunto VARCHAR(500),
                        activo BOOLEAN DEFAULT TRUE,
                        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                        FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL,
                        CONSTRAINT chk_monto_positivo CHECK (monto > 0),
                        CONSTRAINT chk_frecuencia_recurrente CHECK (
                            (es_recurrente = FALSE AND frecuencia IS NULL) OR
                            (es_recurrente = TRUE AND frecuencia IS NOT NULL)
                            )
);
