CREATE TABLE reportes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          cliente_id BIGINT NOT NULL,
                          tipo ENUM('MENSUAL', 'ANUAL', 'PERSONALIZADO') NOT NULL,
                          titulo VARCHAR(255) NOT NULL,
                          descripcion TEXT,
                          fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          fecha_inicio DATE NOT NULL,
                          fecha_fin DATE NOT NULL,
                          archivo_url VARCHAR(500),
                          formato ENUM('PDF', 'EXCEL', 'CSV') DEFAULT 'PDF',
                          activo BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                          CONSTRAINT chk_fecha_reporte CHECK (fecha_inicio <= fecha_fin)
);
