CREATE TABLE presupuestos (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              cliente_id BIGINT NOT NULL,
                              categoria_id BIGINT NULL,
                              monto_presupuestado DECIMAL(10,2) NOT NULL,
                              mes INT NOT NULL,
                              anio INT NOT NULL,
                              activo BOOLEAN DEFAULT TRUE,
                              fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                              FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL,
                              CONSTRAINT chk_mes_valido CHECK (mes >= 1 AND mes <= 12),
                              CONSTRAINT chk_anio_valido CHECK (anio >= 2020 AND anio <= 2050),
                              CONSTRAINT chk_monto_presupuesto_positivo CHECK (monto_presupuestado > 0),
                              UNIQUE KEY unique_presupuesto (cliente_id, categoria_id, mes, anio)
);
