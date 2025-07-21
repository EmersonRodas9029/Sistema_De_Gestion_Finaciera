CREATE TABLE ingresos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          cliente_id BIGINT NOT NULL,
                          monto DECIMAL(10,2) NOT NULL,
                          fecha DATE NOT NULL,
                          tipo ENUM('ESTABLE', 'VOLATIL') NOT NULL,
                          fuente VARCHAR(255),
                          descripcion TEXT,
                          metodo_recepcion ENUM('EFECTIVO', 'TRANSFERENCIA', 'CHEQUE', 'OTRO') DEFAULT 'TRANSFERENCIA',
                          es_recurrente BOOLEAN DEFAULT FALSE,
                          frecuencia ENUM('DIARIO', 'SEMANAL', 'MENSUAL', 'ANUAL') NULL,
                          activo BOOLEAN DEFAULT TRUE,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                          CONSTRAINT chk_monto_ingreso_positivo CHECK (monto > 0),
                          CONSTRAINT chk_frecuencia_ingreso_recurrente CHECK (
                              (es_recurrente = FALSE AND frecuencia IS NULL) OR
                              (es_recurrente = TRUE AND frecuencia IS NOT NULL)
                              )
);
